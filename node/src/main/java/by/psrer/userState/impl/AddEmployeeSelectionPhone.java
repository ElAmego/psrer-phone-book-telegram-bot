package by.psrer.userState.impl;

import by.psrer.dao.DepartmentDAO;
import by.psrer.dao.EmployeeDAO;
import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Department;
import by.psrer.entity.Employee;
import by.psrer.entity.Job;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class AddEmployeeSelectionPhone implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final EmployeeDAO employeeDAO;
    private final DepartmentDAO departmentDAO;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final StringBuilder output = new StringBuilder();

        if (textMessage.matches("^\\+(?:375\\d{9}|80\\d{9})$")) {
            final Employee employee = createEmployee(appUser, textMessage);

            employeeDAO.save(employee);
            messageUtils.changeUserState(appUser, BASIC);
            output.append(employee.getEmployeeName()).append(", ").append(employee.getPhoneNumber())
                    .append("\n").append(employee.getDepartment().getDepartmentName()).append(", ")
                    .append(employee.getJob().getJobName()).append("\n\nСотрудник успешно добавлен в базу данных.");
        } else {
            output.append("Введенный вами номер телефона не соответствует формату (+375293333333 или +80293333333). " +
                    "Введите номер телефона ещё раз или покиньте режим выбора.");
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }

    private Employee createEmployee(final AppUser appUser, final String phoneNumber) {
        final Map<String, Object> intermediateData = appUser.getAppUserConfigId().getIntermediateData();
        final Department department = departmentDAO.findByDepartmentId((int) intermediateData.get("departmentId"));
        final Job job = jobDAO.findByJobId((int) intermediateData.get("jobId"));
        return Employee.builder()
                .employeeName((String) intermediateData.get("fio"))
                .department(department)
                .job(job)
                .phoneNumber(phoneNumber)
                .build();
    }
}