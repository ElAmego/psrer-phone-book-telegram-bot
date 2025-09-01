package by.psrer.controller;

import by.psrer.service.UpdateProducer;
import by.psrer.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.psrer.model.RabbitQueue.CALLBACK;
import static by.psrer.model.RabbitQueue.SENT_MESSAGE;
import static by.psrer.model.RabbitQueue.USER_TEXT_MESSAGE;

@Component
@Log4j
public final class UpdateController {
    private TelegramBot telegramBot;
    private final UpdateProducer updateProducer;

    public UpdateController(final UpdateProducer updateProducer) {
        this.updateProducer = updateProducer;
    }

    public void registerBot(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(final Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else if (update.hasCallbackQuery()) {
            processCallback(update);
        } else {
            log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessagesByType(final Update update) {
        final Message message = update.getMessage();

        if (message.hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processCallback(final Update update) {
        updateProducer.produce(CALLBACK, update);
    }

    private void processTextMessage(final Update update) {
        updateProducer.produce(USER_TEXT_MESSAGE, update);
    }

    private void setUnsupportedMessageTypeView(final Update update) {
        final SendMessage sendMessage = MessageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения");
        setView(sendMessage);
    }

    public void setView(final SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    public void deleteMessageHandler(final DeleteMessage deleteMessage) {
        telegramBot.deleteUserMessage(deleteMessage);
    }

    public void replacedMessageHandler(final EditMessageText replacedMessage) {
        telegramBot.replaceBotMessage(replacedMessage);
    }

    public void processSentMessage(final Message sentMessage) {
        updateProducer.produce(SENT_MESSAGE, sentMessage);
    }
}