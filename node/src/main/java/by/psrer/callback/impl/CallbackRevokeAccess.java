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

import static by.psrer.entity.enums.Status.ACTIVATED;
import static by.psrer.entity.enums.UserState.REVOKE_ACCESS_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackRevokeAccess implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserDAO appUserDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<AppUser> activatedUserList = appUserDAO.findByAppUserConfigIdStatus(ACTIVATED);
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        int inc = 0;

        output.append("Укажите Телеграм ID пользователя из списка у которого вы хотите отозвать доступ.\n");

        for (final AppUser activatedUser: activatedUserList) {
            output.append("\n").append(++inc).append(": ").append(activatedUser.getFirstName()).append(" ")
                    .append(activatedUser.getLastName()).append(", ").append("@").append(activatedUser.getUsername())
                    .append("\nТелеграм ID: ").append(activatedUser.getTelegramUserId());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REVOKE_ACCESS_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}