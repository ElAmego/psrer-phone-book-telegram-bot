package by.psrer.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface ProducerService {
    void produceAnswer(final SendMessage sendMessage);
    void produceDeleteMessage(final DeleteMessage deleteMessage);
    void produceReplacedMessage(final EditMessageText newMessage);
}