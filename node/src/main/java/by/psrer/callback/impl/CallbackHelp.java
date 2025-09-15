package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
public final class CallbackHelp implements Callback {
    private final MessageUtils messageUtils;

    public CallbackHelp(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = """
                Список доступных команд:
                /start – Стартовая страница бота.
                /phones – Телефонная книга.
                /search – Поиск номера по подстроке ФИО.
                /help – Список доступных команд.
                """;

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, null));
    }
}