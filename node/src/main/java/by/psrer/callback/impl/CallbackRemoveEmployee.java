package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Department;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.REMOVE_EMPLOYEE_SELECTION_DEPARTMENT;

@Service
@RequiredArgsConstructor
public final class CallbackRemoveEmployee implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final StringBuilder output = new StringBuilder();
        final List<Department> departmentList = departmentDAO.findAllByOrderByDepartmentIdAsc();

        output.append("Выберите номер отдела, в котором находится необходимый вам сотрудник: ");

        int inc = 0;

        for (final Department department: departmentList) {
            output.append("\n").append(++inc).append(": ").append(department.getDepartmentName());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REMOVE_EMPLOYEE_SELECTION_DEPARTMENT);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}