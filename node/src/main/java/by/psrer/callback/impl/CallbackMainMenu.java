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
public final class CallbackMainMenu implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CallbackMainMenu(MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        String output = "Добро пожаловать в панель администратора " + appUser.getFirstName() + " " +
                appUser.getLastName() + " !" + "\n\n" + "Ваш username: @" + appUser.getUsername() +
                "\nВаш телеграм ID: " + appUser.getTelegramUserId();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createMainMenuButtons();

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createMainMenuButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(buttonFactory.admins());
        inlineKeyboardButtonList.add(buttonFactory.access());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        return inlineKeyboardButtonList;
    }
}