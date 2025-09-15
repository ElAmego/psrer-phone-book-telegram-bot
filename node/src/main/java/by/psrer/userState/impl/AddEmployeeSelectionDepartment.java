package by.psrer.userState.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.entity.Department;
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
import java.util.Optional;

import static by.psrer.entity.enums.UserState.ADD_EMPLOYEE_SELECTION_JOB;

@Service
@RequiredArgsConstructor
public final class AddEmployeeSelectionDepartment implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;
    private final JobDAO jobDAO;
    private final AppUserConfigDAO appUserConfigDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedDepartmentId = Integer.parseInt(textMessage);
            final Optional<Department> selectedDepartment = departmentDAO.findNthSafely(selectedDepartmentId);

            if (selectedDepartment.isPresent()) {
                final Department department = selectedDepartment.get();
                final Long departmentId = department.getDepartmentId();
                final AppUserConfig appUserConfig = appUser.getAppUserConfigId();
                final List<Job> jobList = jobDAO.findAllByOrderByJobIdAsc();

                appUserConfig.getIntermediateData().put("departmentId", departmentId);
                appUserConfigDAO.save(appUserConfig);
                messageUtils.changeUserState(appUser, ADD_EMPLOYEE_SELECTION_JOB);
                output.append("Укажите номер должности для нового сотрудника:\n");

                int inc = 0;
                for (final Job job: jobList) {
                    output.append("\n").append(++inc).append(": ").append(job.getJobName());
                }
            } else {
                output.append("В списке нет выбранного вами значения. Введите корректное значение или покиньте режим " +
                        "выбора.");
            }
        } else {
            output.append("Введенное вами значение не является цифрой. Введите корректное значение или покиньте " +
                    "режим выбора.");
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}