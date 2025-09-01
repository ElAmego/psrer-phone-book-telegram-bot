package by.psrer.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@SuppressWarnings("unused")
@Log4j
public final class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    private final UpdateController updateController;

    public TelegramBot(final UpdateController updateController) {
        this.updateController = updateController;
    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(final SendMessage message) {
        if (message != null) {
            try {
                final Message sentMessage = execute(message);
                updateController.processSentMessage(sentMessage);
            } catch (final TelegramApiException e) {
                log.error(e);
            }
        }
    }

    public void deleteUserMessage(final DeleteMessage deleteMessage) {
        if (deleteMessage != null) {
            try {
                execute(deleteMessage);
            } catch (final TelegramApiException e) {
                log.error(e);
            }
        }
    }

    public void replaceBotMessage (final EditMessageText replacedMessage) {
        if (replacedMessage != null) {
            try {
                execute(replacedMessage);
            } catch (final TelegramApiException e) {
                log.error(e);
            }
        }
    }
}