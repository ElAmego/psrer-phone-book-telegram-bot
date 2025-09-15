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

import static by.psrer.entity.enums.Status.NOT_ACTIVATED;
import static by.psrer.entity.enums.UserState.GRANT_ACCESS_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackGrantAccess implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserDAO appUserDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<AppUser> appUserList = appUserDAO.findByAppUserConfigIdStatus(NOT_ACTIVATED);
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        output.append("Укажите Телеграм ID пользователя из списка, которому вы хотите выдать доступ.\n");

        int inc = 0;
        for (final AppUser appUserFromList: appUserList) {
            output.append("\n").append(++inc).append(": ").append(appUserFromList.getFirstName()).append(" ")
                    .append(appUserFromList.getLastName()).append(", ").append("@")
                    .append(appUserFromList.getUsername()).append("\nТелеграм ID: ")
                    .append(appUserFromList.getTelegramUserId());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, GRANT_ACCESS_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}