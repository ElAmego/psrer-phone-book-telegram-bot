package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class CallbackAreas implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;

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

            inlineKeyboardButtonList.add(buttonFactory.removeArea());
        } else {
            output.append("Список участков в базе данных пуст.");
        }

        inlineKeyboardButtonList.add(buttonFactory.addArea());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}