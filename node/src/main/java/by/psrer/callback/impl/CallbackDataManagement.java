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

@Service
public final class CallbackDataManagement implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CallbackDataManagement(final MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = "Выберите необходимую таблицу для управления данными.";
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createDataManagementButtons();

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createDataManagementButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(buttonFactory.areas());
        inlineKeyboardButtonList.add(buttonFactory.jobs());
        inlineKeyboardButtonList.add(buttonFactory.departments());
        inlineKeyboardButtonList.add(buttonFactory.employees());
        inlineKeyboardButtonList.add(buttonFactory.mainMenu());
        return inlineKeyboardButtonList;
    }
}