package by.psrer.service.impl;

import by.psrer.command.UserCommandContainer;
import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.entity.enums.UserState;
import by.psrer.service.MainService;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static by.psrer.entity.enums.Role.USER;
import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public final class MainServiceImpl implements MainService {
    private final AppUserDAO appUserDAO;
    private final AppUserConfigDAO appUserConfigDAO;
    private final UserCommandContainer userCommandContainer;
    private final MessageUtils messageUtils;

    @Override
    public void handleCommand(final Update update) {
        final AppUser appUser = findOrSaveAppUser(update);

        processServiceCommand(update, appUser);
    }

    private void processServiceCommand(final Update update, final AppUser appUser) {
        final String textMessage = update.getMessage().getText();
        final UserState userState = appUser.getAppUserConfigId().getUserState();

        if (userState == BASIC) {
            if (textMessage.startsWith("/")) {
                final String cmd = textMessage.split(" ")[0].toLowerCase();
                userCommandContainer.retrieveCommand(cmd).execute(appUser);
            } else {
                messageUtils.sendTextMessage(appUser.getTelegramUserId(),
                        new Answer("Введенный вами текст не является командой. Список доступных команд – " +
                                "/help",null));
            }
        }
    }

    private AppUser findOrSaveAppUser(final Update update) {
        final User user = update.getMessage().getFrom();
        final AppUser persistanceAppUser = appUserDAO.findAppUserByTelegramUserId(user.getId());
        if (persistanceAppUser == null) {
            AppUserConfig appUserConfig = AppUserConfig.builder()
                    .role(USER)
                    .userState(BASIC)
                    .build();

            appUserConfig = appUserConfigDAO.save(appUserConfig);

            final AppUser transientAppUser = AppUser.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUserName())
                    .telegramUserId(user.getId())
                    .appUserConfigId(appUserConfig)
                    .build();

            return appUserDAO.save(transientAppUser);
        }

        return persistanceAppUser;
    }
}