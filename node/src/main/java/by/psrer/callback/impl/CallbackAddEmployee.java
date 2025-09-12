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

import static by.psrer.entity.enums.UserState.ADD_EMPLOYEE_SELECTION_DEPARTMENT;

@Service
@RequiredArgsConstructor
public final class CallbackAddEmployee implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        final List<Department> departmentList = departmentDAO.findAllByOrderByDepartmentIdAsc();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (!departmentList.isEmpty()) {
            output.append("Укажите номер отдела к которому должен быть прикреплен сотрудник (Например: 1):\n");

            int inc = 0;
            for (final Department department: departmentList) {
                output.append("\n").append(++inc).append(": ").append(department.getDepartmentName());
            }

            inlineKeyboardButtonList.add(buttonFactory.cancel());
            messageUtils.changeUserState(appUser, ADD_EMPLOYEE_SELECTION_DEPARTMENT);
        } else {
            output.append("В базе данных отсутствуют отделы или должности. Добавьте сначала отдел и должность, а затем " +
                    "сотрудника.\nВы покинули режим выбора.");
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}