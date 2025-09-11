package by.psrer.userState.impl;

import by.psrer.dao.EmployeeDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.entity.Employee;
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
public final class RemoveEmployeeSelectionEmployee implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final EmployeeDAO employeeDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final String output;
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final AppUserConfig appUserConfig = appUser.getAppUserConfigId();
            final int selectedEmployeeId = Integer.parseInt(textMessage);
            final int departmentId = (int) appUserConfig.getIntermediateData().get("departmentId");
            final Optional<Employee> selectedEmployee = employeeDAO.findNthSafely(selectedEmployeeId, departmentId);

            if (selectedEmployee.isPresent()) {
                final Employee employee = selectedEmployee.get();

                employeeDAO.deleteEmployeeByEmployeeId(employee.getEmployeeId());
                messageUtils.changeUserState(appUser, BASIC);

                output = "Сотрудник " + employee.getEmployeeName() + " успешно удален из базы данных.";
            } else {
                output = "В списке нет выбранного вами значения. Введите корректное значение или покиньте режим " +
                        "выбора.";
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