package by.psrer.userState.impl;

import by.psrer.dao.DepartmentDAO;
import by.psrer.dao.EmployeeDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Department;
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
public final class DepartmentSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedDepartmentId = Integer.parseInt(textMessage);
            final Long areaId = ((Number) appUser.getAppUserConfigId().getIntermediateData().get("areaId")).longValue();
            final Optional<Department> selectedDepartment = departmentDAO.findNthSafely(selectedDepartmentId, areaId);

            if (selectedDepartment.isPresent()) {
                final Long departmentId = selectedDepartment.get().getDepartmentId();
                final List<Employee> employeeList = employeeDAO
                        .findByDepartment_DepartmentIdOrderByEmployeeIdAsc(departmentId);

                if (!employeeList.isEmpty()) {
                    output.append("Список номеров:");

                    int inc = 0;

                    for (final Employee employeeFromList: employeeList) {
                        output.append("\n").append(++inc).append(": ").append(employeeFromList.getEmployeeName())
                                .append("\n").append(employeeFromList.getJob().getJobName()).append(",\n")
                                .append(employeeFromList.getPhoneNumber());
                    }

                    output.append("\n\nВы покинули режим выбора.");
                } else {
                    output.append("Список работников выбранного вами отдела пуст. Вы вышли из режима выбора.");
                }

                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output.append("""
                    В списке нет выбранного вами значения. Введите корректное значение или нажмите на кнопку \
                    "Покинуть режим выбора".
                    """);
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }
        } else {
            output.append("""
                    Введенное вами значение не является цифрой. Введите корректное значение или нажмите на кнопку \
                    "Покинуть режим выбора".
                    """);
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}