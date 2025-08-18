package by.psrer.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumer {
    void consumeAnswer(final SendMessage sendMessage);
}