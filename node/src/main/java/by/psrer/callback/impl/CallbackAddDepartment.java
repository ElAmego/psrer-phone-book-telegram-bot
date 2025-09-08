package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.UserState.ADD_DEPARTMENT_SELECTION_AREA;

@Service
public final class CallbackAddDepartment implements Callback {
    private final MessageUtils messageUtils;
    private final AreaDAO areaDAO;

    public CallbackAddDepartment(final MessageUtils messageUtils, final AreaDAO areaDAO) {
        this.messageUtils = messageUtils;
        this.areaDAO = areaDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        List<InlineKeyboardButton> inlineKeyboardButtonList = null;
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();

        if (!areaList.isEmpty()) {
            int inc = 0;
            output.append("Укажите номер участка к которому должен быть прикреплен отдел (Например: 1):");

            for (final Area area: areaList) {
                output.append("\n").append(++inc).append(": ").append(area.getAreaName());
            }

            messageUtils.changeUserState(appUser, ADD_DEPARTMENT_SELECTION_AREA);
            inlineKeyboardButtonList = messageUtils.createCancelCommand();
        } else {
            output.append("В базе данных отсутствуют участки. Добавьте сначала участок, а затем отдел.");
        }
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}