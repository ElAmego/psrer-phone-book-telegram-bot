package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.UserState.GRANT_ACCESS_SELECTION;

@Service
public final class CallbackGrantAccess implements Callback {
    private final MessageUtils messageUtils;

    public CallbackGrantAccess(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final String output = """
                Вы перешли в режим выбора. Введите телеграм ID пользователя которому хотите выдать доступ, например: \
                13432334
                
                Нажмите на кнопку "Покинуть режима выбора" чтобы выйти из режима выбора.
                """;

        final List<InlineKeyboardButton> cancelBtn = messageUtils.createCancelCommand();

        messageUtils.changeUserState(appUser, GRANT_ACCESS_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, cancelBtn));
    }
}