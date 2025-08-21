package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
public final class CallbackCancel implements Callback {
    private final MessageUtils messageUtils;

    public CallbackCancel(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        messageUtils.sendTextMessage(chatId, new Answer("Вы вышли из режима выбора.",
                null));
        messageUtils.changeUserState(appUser, BASIC);
    }
}