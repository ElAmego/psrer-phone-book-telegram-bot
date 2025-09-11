package by.psrer.userState.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.ADD_EMPLOYEE_SELECTION_PHONE;

@Service
@RequiredArgsConstructor
public final class AddEmployeeSelectionFio implements UserStateHandler {
    private final static int TEXT_MESSAGE_LIMIT = 255;
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserConfigDAO appUserConfigDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

        if (textMessage.length() <= TEXT_MESSAGE_LIMIT) {
            final AppUserConfig appUserConfig = appUser.getAppUserConfigId();

            appUserConfig.getIntermediateData().put("fio", textMessage);
            appUserConfigDAO.save(appUserConfig);
            messageUtils.changeUserState(appUser, ADD_EMPLOYEE_SELECTION_PHONE);
            output = "Введите номер телефона сотрудника (Формат: +375293333333 или +80293333333): ";
        } else {
            output = "Вы превысили лимит сообщения в 255 символов. Введите заново или покиньте режим выбора.";
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}