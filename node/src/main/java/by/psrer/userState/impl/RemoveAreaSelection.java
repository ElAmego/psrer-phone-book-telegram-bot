package by.psrer.userState.impl;

import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
public final class RemoveAreaSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final AreaDAO areaDAO;

    public RemoveAreaSelection(final MessageUtils messageUtils, final AreaDAO areaDAO) {
        this.messageUtils = messageUtils;
        this.areaDAO = areaDAO;
    }

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        String output;
        List<InlineKeyboardButton> inlineKeyboardButtonList = null;

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedDepartmentId = Integer.parseInt(textMessage);
            final Optional<Area> selectedArea = areaDAO.findNthSafely(selectedDepartmentId);

            if (selectedArea.isPresent()) {
                final Area area = selectedArea.get();
                final Long areaId = area.getAreaId();

                areaDAO.deleteAreaByAreaId(areaId);
                output = "Участок " + area.getAreaName() + " успешно удален из базы данных.";
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "В списке нет выбранного вами значения. Введите корректное значение или покиньте режим выбора.";
                inlineKeyboardButtonList = messageUtils.createCancelCommand();
            }
        } else {
            output = "Введенное вами значение не является цифрой. Введите корректное значение или покиньте режим " +
                    "выбора.";
            inlineKeyboardButtonList = messageUtils.createCancelCommand();
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}