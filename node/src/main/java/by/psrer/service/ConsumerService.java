package by.psrer.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeUserTextMessage(final Update update);
}