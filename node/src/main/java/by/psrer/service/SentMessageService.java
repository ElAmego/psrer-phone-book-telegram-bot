package by.psrer.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface SentMessageService {
    void handleSentMessage(final Message sentMessage);
}