package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.Role.ADMIN;

@Service
@SuppressWarnings("unused")
public final class CallbackAdmins implements Callback {
    private final MessageUtils messageUtils;
    private final AppUserDAO appUserDAO;

    public CallbackAdmins(final MessageUtils messageUtils, final AppUserDAO appUserDAO) {
        this.messageUtils = messageUtils;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder("Список администраторов: ");
        final Long chatId = appUser.getTelegramUserId();
        final List<AppUser> appUserList = appUserDAO.findByAppUserConfigIdRole(ADMIN);
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createAdminsButtons();

        if (!appUserList.isEmpty()) {
            int inc = 0;
            for (final AppUser appUserFromList: appUserList) {
                output.append("\n").append(++inc).append(": ").append(appUserFromList.getFirstName()).append(" ")
                        .append(appUserFromList.getLastName()).append(", ").append(appUserFromList.getUsername())
                        .append("\nТелеграм ID пользователя: ").append(appUserFromList.getTelegramUserId());
            }
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(),
                inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createAdminsButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Добавить")
                .callbackData("addAdminBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Убрать")
                .callbackData("removeAdminBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Главное меню")
                .callbackData("mainMenuBtn")
                .build());

        return inlineKeyboardButtonList;
    }
}