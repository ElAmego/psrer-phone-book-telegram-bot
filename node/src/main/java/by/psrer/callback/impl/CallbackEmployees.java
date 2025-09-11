package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.EmployeeDAO;
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
public final class CallbackEmployees implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final EmployeeDAO employeeDAO;

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        final long employeeQuantity = employeeDAO.count();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (employeeQuantity != 0) {
            output.append("Количество сотрудников в базе данных: ").append(employeeQuantity);
            inlineKeyboardButtonList.add(buttonFactory.removeEmployee());
        } else {
            output.append("В базе данных отсутствуют сотрудники.");
        }

        inlineKeyboardButtonList.add(buttonFactory.addEmployee());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}