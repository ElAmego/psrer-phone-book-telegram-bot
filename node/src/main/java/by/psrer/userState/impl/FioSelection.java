package by.psrer.userState.impl;

import by.psrer.dao.EmployeeDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Employee;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public final class FioSelection implements UserStateHandler {
    private final EmployeeDAO employeeDAO;
    private final MessageUtils messageUtils;

    public FioSelection(final EmployeeDAO employeeDAO, final MessageUtils messageUtils) {
        this.employeeDAO = employeeDAO;
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final StringBuilder output = new StringBuilder();
        final Long chatId = appUser.getTelegramUserId();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = messageUtils.createCancelCommand();
        final List<Employee> employeeList = employeeDAO.
                findByEmployeeNameContainingIgnoreCaseOrderByEmployeeNameAsc(textMessage);

        if (!employeeList.isEmpty()) {
            output.append("Список работников по заданной подстроке \"").append(textMessage).append("\":");
            int inc = 0;
            for (final Employee employeeFromList: employeeList) {
                output.append("\n").append(++inc).append(": ").append(employeeFromList.getEmployeeName())
                        .append("\n").append(employeeFromList.getDepartment().getDepartmentName()).append(", ")
                        .append(employeeFromList.getJob().getJobName()).append("\n")
                        .append(employeeFromList.getPhoneNumber());
            }
        } else {
            output.append("Нет данных по заданной подстроке.");
        }

        output.append("""
                
                
                Введите новую подстроку или нажмите на кнопку "Покинуть режим выбора" чтобы выйти из \
                режима выбора.
                """);
        messageUtils.sendTextMessage(chatId, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}