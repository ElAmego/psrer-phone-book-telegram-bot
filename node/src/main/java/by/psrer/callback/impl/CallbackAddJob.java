package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.ADD_JOB_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackAddJob implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final String output = "Введите название должности, которую вы хотите добавить в базу данных: ";

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, ADD_JOB_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}