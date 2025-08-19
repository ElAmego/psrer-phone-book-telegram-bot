package by.psrer.utils.impl;

import by.psrer.service.ProducerService;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public final class MessageUtilsImpl implements MessageUtils {
    private static final int TELEGRAM_MESSAGE_LIMIT = 3000;
    private final ProducerService producerService;

    @Override
    public void sendTextMessage(final Long chatId, final Answer answer) {
        final int sendMessageLength = answer.answerText().length();

        if (sendMessageLength > TELEGRAM_MESSAGE_LIMIT) {
            final List<Answer> answers = splitAnswer(answer);
            for (final Answer answerFromList : answers) {
                sendTextMessage(chatId, answerFromList);
            }

            return;
        }

        final List<InlineKeyboardButton> inlineKeyboardButtonList = answer.inlineKeyboardButtonList();

        final SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(answer.answerText())
                .build();

        // TODO сделать обработку кнопок

        producerService.produceAnswer(sendMessage);
    }

    private List<Answer> splitAnswer(final Answer answer) {
        final List<Answer> answers = new ArrayList<>();
        final String mainAnswerText = answer.answerText();
        final int outputLength = mainAnswerText.length();

        for (int i = 0; i < outputLength; i += TELEGRAM_MESSAGE_LIMIT) {
            int end = Math.min(i + TELEGRAM_MESSAGE_LIMIT, outputLength);
            final String chunk = mainAnswerText.substring(i, end);

            if (i + TELEGRAM_MESSAGE_LIMIT >= outputLength) {
                answers.add(new Answer(chunk, answer.inlineKeyboardButtonList()));
            } else {
                answers.add(new Answer(chunk, null));
            }
        }

        return answers;
    }
}