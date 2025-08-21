package by.psrer.utils.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.entity.enums.UserState;
import by.psrer.service.ProducerService;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.Role.USER;
import static by.psrer.entity.enums.Status.NOT_ACTIVATED;
import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public final class MessageUtilsImpl implements MessageUtils {
    private static final int TELEGRAM_MESSAGE_LIMIT = 2000;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final AppUserConfigDAO appUserConfigDAO;

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

        if (inlineKeyboardButtonList != null) {
            sendMessage.setReplyMarkup(InlineKeyboardMarkup.builder()
                    .keyboard(List.of(
                            inlineKeyboardButtonList))
                    .build());
        }

        producerService.produceAnswer(sendMessage);
    }

    @Override
    public AppUser findOrSaveAppUser(final Update update) {
        final User user = update.getMessage().getFrom();
        final AppUser persistanceAppUser = appUserDAO.findAppUserByTelegramUserId(user.getId());
        if (persistanceAppUser == null) {
            AppUserConfig appUserConfig = AppUserConfig.builder()
                    .role(USER)
                    .userState(BASIC)
                    .status(NOT_ACTIVATED)
                    .build();

            appUserConfig = appUserConfigDAO.save(appUserConfig);

            final AppUser transientAppUser = AppUser.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUserName())
                    .telegramUserId(user.getId())
                    .appUserConfigId(appUserConfig)
                    .build();

            return appUserDAO.save(transientAppUser);
        }

        return persistanceAppUser;
    }

    @Override
    public List<InlineKeyboardButton> createHelpCommand() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Список команд")
                .callbackData("helpBtn")
                .build());

        return inlineKeyboardButtonList;
    }

    @Override
    public List<InlineKeyboardButton> createCancelCommand() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Покинуть режим выбора")
                .callbackData("cancelBtn")
                .build());

        return inlineKeyboardButtonList;
    }

    @Override
    public void changeUserState(final AppUser appUser, final UserState userState) {
        final AppUserConfig appUserConfig = appUser.getAppUserConfigId();
        appUserConfig.setUserState(userState);
        appUserConfigDAO.save(appUserConfig);
    }

    @Override
    public void changeUserStateWithIntermediateValue(final AppUser appUser, final UserState userState,
                                                     final Long intermediateValue) {
        final AppUserConfig appUserConfig = appUser.getAppUserConfigId();
        appUserConfig.setUserState(userState);
        appUserConfig.setIntermediateValue(intermediateValue);
        appUserConfigDAO.save(appUserConfig);
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