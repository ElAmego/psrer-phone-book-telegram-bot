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
import static by.psrer.entity.enums.Status.NOT_ACTIVATED;

@Service
@RequiredArgsConstructor
public final class CallbackAccess implements Callback {
    private final MessageUtils messageUtils;
    private final AppUserDAO appUserDAO;
    private final ButtonFactory buttonFactory;

    @Override
    public void execute(final AppUser appUser) {
        final long unActivatedUserQuantity = appUserDAO.countByAppUserConfigId_Status(NOT_ACTIVATED);
        final long activatedUserQuantity = appUserDAO.countByAppUserConfigId_Status(ACTIVATED);
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final String output = "Количество активированных пользователей бота: " + activatedUserQuantity +
                "\nКоличество пользователей, ожидающих активацию: " + unActivatedUserQuantity;

        if (unActivatedUserQuantity != 0) {
            inlineKeyboardButtonList.add(buttonFactory.grantAccess());
        }

        if (activatedUserQuantity > 1) {
            inlineKeyboardButtonList.add(buttonFactory.revokeAccess());
        }

        inlineKeyboardButtonList.add(buttonFactory.mainMenu());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}