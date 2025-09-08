package by.psrer.userState.impl;

import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Department;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class RemoveDepartmentSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedDepartmentId = Integer.parseInt(textMessage);
            final Optional<Department> selectedDepartment = departmentDAO.findNthSafely(selectedDepartmentId);

            if (selectedDepartment.isPresent()) {
                final Department department = selectedDepartment.get();
                final Long departmentId = department.getDepartmentId();

                departmentDAO.deleteDepartmentByDepartmentId(departmentId);
                output = "Отдел \"" + department.getDepartmentName() + "\" успешно удален из базы данных.";
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "В списке нет выбранного вами значения. Введите корректное значение или покиньте режим выбора.";
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }
        } else {
            output = "Введенное вами значение не является цифрой. Введите корректное значение или покиньте режим " +
                    "выбора.";
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}