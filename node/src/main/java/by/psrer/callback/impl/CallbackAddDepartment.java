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

import static by.psrer.entity.enums.UserState.ADD_DEPARTMENT_SELECTION_AREA;

@Service
@RequiredArgsConstructor
public final class CallbackAddDepartment implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();

        if (!areaList.isEmpty()) {
            output.append("Укажите номер участка к которому должен быть прикреплен отдел (Например: 1):\n");

            int inc = 0;
            for (final Area area: areaList) {
                output.append("\n").append(++inc).append(": ").append(area.getAreaName());
            }

            messageUtils.changeUserState(appUser, ADD_DEPARTMENT_SELECTION_AREA);
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        } else {
            output.append("В базе данных отсутствуют участки. Добавьте сначала участок, а затем отдел.");
        }
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}