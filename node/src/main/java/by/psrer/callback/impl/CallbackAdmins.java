package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.Role.ADMIN;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public final class CallbackAdmins implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserDAO appUserDAO;

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
        inlineKeyboardButtonList.add(buttonFactory.addAdmin());
        inlineKeyboardButtonList.add(buttonFactory.removeAdmin());
        inlineKeyboardButtonList.add(buttonFactory.mainMenu());
        return inlineKeyboardButtonList;
    }
}