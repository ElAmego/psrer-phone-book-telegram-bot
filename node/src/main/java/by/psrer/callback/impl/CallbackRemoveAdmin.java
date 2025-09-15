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
import static by.psrer.entity.enums.UserState.REMOVE_ADMIN_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackRemoveAdmin implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserDAO appUserDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<AppUser> adminList = appUserDAO.findByAppUserConfigIdRole(ADMIN);
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        output.append("Укажите Телеграм ID пользователя из списка у которого вы хотите отозвать доступ.\n");

        int inc = 0;
        for (final AppUser admin: adminList) {
            output.append("\n").append(++inc).append(": ").append(admin.getFirstName()).append(" ")
                    .append(admin.getLastName()).append(", ").append("@").append(admin.getUsername())
                    .append("\nТелеграм ID: ").append(admin.getTelegramUserId());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REMOVE_ADMIN_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}