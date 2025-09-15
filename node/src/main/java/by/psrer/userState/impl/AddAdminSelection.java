package by.psrer.userState.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
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

import static by.psrer.entity.enums.Role.ADMIN;
import static by.psrer.entity.enums.Status.ACTIVATED;
import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class AddAdminSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AppUserDAO appUserDAO;
    private final AppUserConfigDAO appUserConfigDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

        if (textMessage.matches("[-+]?\\d+")) {
            final Long selectedAppUserId = Long.valueOf(textMessage);
            final AppUser selectedAppUser = appUserDAO.findAppUserByTelegramUserId(selectedAppUserId);

            if (selectedAppUser != null) {
                final AppUserConfig appUserConfig = selectedAppUser.getAppUserConfigId();

                if (appUserConfig.getRole() != ADMIN) {
                    final String notification = "Ваша роль сменилась на ADMIN.";
                    output = "Администратор добавлен. Вы покинули режим выбора.";

                    appUserConfig.setStatus(ACTIVATED);
                    appUserConfig.setRole(ADMIN);
                    appUserConfigDAO.save(appUserConfig);

                    messageUtils.changeUserState(appUser, BASIC);
                    messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
                    messageUtils.sendTextMessage(selectedAppUserId, new Answer(notification, null));
                } else {
                    output = (" Пользователь уже является администратором. Введите заново или покиньте режим выбора.");
                    inlineKeyboardButtonList.add(buttonFactory.cancel());
                }
            } else {
                output = ("Пользователя с таким id нет в базе данных. Введите заново или покиньте режим выбора.");
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }

        } else {
            output = ("Вы ввели недопустимое значение! Телеграм ID состоит только из цифр. Введите заново или " +
                    "покиньте режим выбора.");
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}