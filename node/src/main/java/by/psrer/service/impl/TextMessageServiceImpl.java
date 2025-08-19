package by.psrer.service.impl;

import by.psrer.command.UserCommandContainer;
import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.enums.UserState;
import by.psrer.service.TextMessageService;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public final class TextMessageServiceImpl implements TextMessageService {
    private final AppUserDAO appUserDAO;
    private final AppUserConfigDAO appUserConfigDAO;
    private final UserCommandContainer userCommandContainer;
    private final MessageUtils messageUtils;

    @Override
    public void handleCommand(final Update update) {
        final AppUser appUser = messageUtils.findOrSaveAppUser(update);
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
}