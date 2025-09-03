package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.Status.NOT_ACTIVATED;
import static by.psrer.entity.enums.UserState.GRANT_ACCESS_SELECTION;

@Service
public final class CallbackGrantAccess implements Callback {
    private final MessageUtils messageUtils;
    private final AppUserDAO appUserDAO;

    public CallbackGrantAccess(final MessageUtils messageUtils, final AppUserDAO appUserDAO) {
        this.messageUtils = messageUtils;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final List<AppUser> appUserList = appUserDAO.findByAppUserConfigIdStatus(NOT_ACTIVATED);
        final StringBuilder output = new StringBuilder();
        List<InlineKeyboardButton> inlineKeyboardButtonList = null;

        if (!appUserList.isEmpty()) {
            int inc = 0;
            output.append("""
                Вы перешли в режим выбора. Введите телеграм ID пользователя которому хотите выдать доступ, например: \
                13432334.
                
                Список пользователей без доступа:
                """);

            for (final AppUser appUserFromList: appUserList) {
                output.append("\n").append(++inc).append(": ").append(appUserFromList.getFirstName()).append(" ")
                        .append(appUserFromList.getLastName()).append(", ").append("@")
                        .append(appUserFromList.getUsername()).append("\nТелеграм ID: ")
                        .append(appUserFromList.getTelegramUserId());
            }

            output.append("\n\nНажмите на кнопку \"Покинуть режима выбора\" чтобы выйти из режима выбора.");

            inlineKeyboardButtonList = messageUtils.createCancelCommand();

            messageUtils.changeUserState(appUser, GRANT_ACCESS_SELECTION);
        } else {
            output.append("Список не активированных пользователей пуст.");
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}