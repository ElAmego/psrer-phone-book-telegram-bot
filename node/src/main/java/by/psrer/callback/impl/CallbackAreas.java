package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public final class CallbackAreas implements Callback {
    private final MessageUtils messageUtils;
    private final AreaDAO areaDAO;

    public CallbackAreas(final MessageUtils messageUtils, final AreaDAO areaDAO) {
        this.messageUtils = messageUtils;
        this.areaDAO = areaDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (!areaList.isEmpty()) {
            output.append("Список участков: ");
            int inc = 0;

            for (final Area area: areaList) {
                output.append("\n").append(++inc).append(": ").append(area.getAreaName());
            }

            inlineKeyboardButtonList.add(createDeleteAreaBtn());
        } else {
            output.append("Список участков в базе данных пуст.");
        }

        inlineKeyboardButtonList.add(createAddAreaBtn());
        inlineKeyboardButtonList.add(createDataManagementBtn());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }

    private InlineKeyboardButton createAddAreaBtn() {
        return InlineKeyboardButton.builder()
                .text("Добавить участок")
                .callbackData("addAreaBtn")
                .build();
    }

    private InlineKeyboardButton createDeleteAreaBtn() {
        return InlineKeyboardButton.builder()
                .text("Удалить участок")
                .callbackData("removeAreaBtn")
                .build();
    }

    private InlineKeyboardButton createDataManagementBtn() {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("dataManagementBtn")
                .build();
    }
}