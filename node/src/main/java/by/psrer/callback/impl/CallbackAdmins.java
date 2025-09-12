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
        final long adminQuantity = appUserDAO.countByAppUserConfigId_Role(ADMIN);
        final List<InlineKeyboardButton> inlineKeyboardButtonList =  new ArrayList<>();
        final String output = "Количество администраторов бота: " + adminQuantity;

        if (adminQuantity > 1) {
            inlineKeyboardButtonList.add(buttonFactory.removeAdmin());
        }

        inlineKeyboardButtonList.add(buttonFactory.addAdmin());
        inlineKeyboardButtonList.add(buttonFactory.mainMenu());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}