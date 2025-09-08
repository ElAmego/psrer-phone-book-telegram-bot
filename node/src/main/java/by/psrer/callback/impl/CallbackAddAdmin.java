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

import static by.psrer.entity.enums.UserState.ADD_ADMIN_SELECTION;

@Service
public final class CallbackAddAdmin implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CallbackAddAdmin(final MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final String output = """
                Вы перешли в режим выбора. Введите телеграм ID пользователя которому хотите выдать права \
                администратора, например: 13432334
                
                Нажмите на кнопку "Покинуть режима выбора" чтобы выйти из режима выбора.
                """;
        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, ADD_ADMIN_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}