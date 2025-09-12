package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class CallbackDepartments implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final DepartmentDAO departmentDAO;

    @Override
    public void execute(final AppUser appUser) {
       String output;
        final long departmentQuantity = departmentDAO.count();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (departmentQuantity != 0) {
            output = "Количество отделов в базе данных: " + departmentQuantity;

            inlineKeyboardButtonList.add(buttonFactory.removeDepartment());
        } else {
            output = "Список отделов в базе данных пуст.";
        }

        inlineKeyboardButtonList.add(buttonFactory.addDepartment());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}