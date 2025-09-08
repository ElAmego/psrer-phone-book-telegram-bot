package by.psrer.userState.impl;

import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class AddAreaSelection implements UserStateHandler {
    private final static int TEXT_MESSAGE_LIMIT = 255;
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

        if (textMessage.length() <= TEXT_MESSAGE_LIMIT) {
            final Area selectedArea = areaDAO.findByAreaName(textMessage);

            if (selectedArea == null) {
                output = "Участок \"" + textMessage + "\" успешно добавлен в базу данных.";
                final Area newArea = Area.builder()
                        .areaName(textMessage)
                        .build();

                areaDAO.save(newArea);
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "Такая запись уже есть в базе данных. Введите заново или покиньте режим выбора.";
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }
        } else {
            output = "Вы превысили лимит сообщения в 255 символов. Введите заново или покиньте режим выбора.";
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}