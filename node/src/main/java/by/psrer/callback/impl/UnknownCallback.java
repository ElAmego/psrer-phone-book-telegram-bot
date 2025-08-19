package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public final class UnknownCallback implements Callback {
    private final MessageUtils messageUtils;

    public UnknownCallback(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        messageUtils.sendTextMessage(chatId, new Answer("Недоступно", null));
    }
}