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

import static by.psrer.entity.enums.UserState.REMOVE_AREA_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackRemoveArea implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();
        final StringBuilder output = new StringBuilder();

        output.append("Введите номер участка из списка, который вы хотите удалить из базы данных (Например 1):\n");

        int inc = 0;

        for (final Area area: areaList) {
            output.append("\n").append(++inc).append(": ").append(area.getAreaName());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REMOVE_AREA_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}