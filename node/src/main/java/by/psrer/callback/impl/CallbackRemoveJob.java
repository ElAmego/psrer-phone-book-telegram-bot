package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.REMOVE_JOB_SELECTION;

@Service
public final class CallbackRemoveJob implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CallbackRemoveJob(final MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final String output = "Введите номер должности из списка, который вы хотите удалить из базы данных (Например 1): ";

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REMOVE_JOB_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}