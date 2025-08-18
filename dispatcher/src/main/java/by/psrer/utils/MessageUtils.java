package by.psrer.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class MessageUtils {
    public static SendMessage generateSendMessageWithText(final Update update, final String text) {
        final Message message = update.getMessage();

        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(text)
                .build();
    }
}