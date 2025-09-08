package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Department;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public final class CallbackDepartments implements Callback {
    private final MessageUtils messageUtils;
    private final DepartmentDAO departmentDAO;

    public CallbackDepartments(final MessageUtils messageUtils, final DepartmentDAO departmentDAO) {
        this.messageUtils = messageUtils;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        final List<Department> departmentList = departmentDAO.findAllByOrderByDepartmentIdAsc();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (!departmentList.isEmpty()) {
            output.append("Список отделов: ");
            int inc = 0;

            for (final Department department: departmentList) {
                output.append("\n").append(++inc).append(": ").append(department.getDepartmentName());
            }

            inlineKeyboardButtonList.add(createDeleteDepartmentBtn());
        } else {
            output.append("Список отделов в базе данных пуст.");
        }

        inlineKeyboardButtonList.add(createAddDepartmentBtn());
        inlineKeyboardButtonList.add(createDataManagementBtn());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }

    private InlineKeyboardButton createAddDepartmentBtn() {
        return InlineKeyboardButton.builder()
                .text("Добавить отдел")
                .callbackData("addDepartmentBtn")
                .build();
    }

    private InlineKeyboardButton createDeleteDepartmentBtn() {
        return InlineKeyboardButton.builder()
                .text("Удалить отдел")
                .callbackData("removeDepartmentBtn")
                .build();
    }

    private InlineKeyboardButton createDataManagementBtn() {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("dataManagementBtn")
                .build();
    }
}