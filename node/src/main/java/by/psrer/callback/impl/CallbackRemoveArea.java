package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.UserState.REMOVE_AREA_SELECTION;

@Service
public final class CallbackRemoveArea implements Callback {
    private final MessageUtils messageUtils;

    public CallbackRemoveArea(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = "Введите номер участка из списка, который вы хотите удалить из базы данных (Например 1): ";
        final List<InlineKeyboardButton> inlineKeyboardButtonList = messageUtils.createCancelCommand();

        messageUtils.changeUserState(appUser, REMOVE_AREA_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}