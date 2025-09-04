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
public final class CallbackDataManagement implements Callback {
    private final MessageUtils messageUtils;

    public CallbackDataManagement(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = "Выберите необходимую таблицу для управления данными.";
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createDataManagementButtons();

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createDataManagementButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Участки")
                .callbackData("areasBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Должности")
                .callbackData("jobsBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Отделы")
                .callbackData("departmentsBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Сотрудники")
                .callbackData("employeesBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Главное меню")
                .callbackData("mainMenuBtn")
                .build());

        return inlineKeyboardButtonList;
    }
}