package by.psrer.service.impl;

import by.psrer.command.UserCommandContainer;
import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.enums.Status;
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
        if (!checkStatusAccount(appUser)) return;

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

    private boolean checkStatusAccount(final AppUser appUser) {
        final Status userAccountStatus = appUser.getAppUserConfigId().getStatus();
        final Long chatId = appUser.getTelegramUserId();

        if (userAccountStatus == Status.NOT_ACTIVATED) {
            messageUtils.sendTextMessage(chatId,
                    new Answer("Ваш аккаунт не активирован! Заявка отправлена, ожидайте.",
                            null));
            return false;
        }

        if (userAccountStatus == Status.BLOCKED) {
            messageUtils.sendTextMessage(chatId,
                    new Answer("Вы находитесь в черном списке.",
                            null));
            return false;
        }

        return true;
    }
}