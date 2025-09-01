package by.psrer.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@SuppressWarnings("unused")
public interface ConsumerService {
    void consumeUserTextMessage(final Update update);
    void consumeCallback(final Update update);
    void consumeSentMessage(final Message sentMessage);
}