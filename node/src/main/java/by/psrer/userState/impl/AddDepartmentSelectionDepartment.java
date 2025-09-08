package by.psrer.userState.impl;

import by.psrer.dao.AreaDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.entity.Department;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class AddDepartmentSelectionDepartment implements UserStateHandler {
    private final static int TEXT_MESSAGE_LIMIT = 255;
    private final MessageUtils messageUtils;
    private final DepartmentDAO departmentDAO;
    private final AreaDAO areaDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        String output;
        List<InlineKeyboardButton> inlineKeyboardButtonList = null;

        if (textMessage.length() <= TEXT_MESSAGE_LIMIT) {
            final Department selectedDepartment = departmentDAO.findByDepartmentName(textMessage);

            if (selectedDepartment == null) {
                var areaId = appUser.getAppUserConfigId().getIntermediateData().get("areaId");
                final Area area = areaDAO.findByAreaId((Integer) areaId);
                output = "Отдел \"" + textMessage + "\" успешно добавлен в базу данных.";
                final Department newDepartment = Department.builder()
                        .departmentName(textMessage)
                        .area(area)
                        .build();

                departmentDAO.save(newDepartment);
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "Такая запись уже есть в базе данных. Введите заново или покиньте режим выбора.";
                inlineKeyboardButtonList = messageUtils.createCancelCommand();
            }
        } else {
            output = "Вы превысили лимит сообщения в 255 символов. Введите заново или покиньте режим выбора.";
            inlineKeyboardButtonList = messageUtils.createCancelCommand();
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}