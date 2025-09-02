package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public final class CallbackMainMenu implements Callback {
    private final MessageUtils messageUtils;

    public CallbackMainMenu(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = "Добро пожаловать в панель администратора!";
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createAdminButtons();

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createAdminButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Администраторы")
                .callbackData("adminsBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Выдать доступ")
                .callbackData("grantBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Отозвать доступ")
                .callbackData("revokeBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Добавление данных")
                .callbackData("addDataBtn")
                .build());

        return inlineKeyboardButtonList;
    }
}