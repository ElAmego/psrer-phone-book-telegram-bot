package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.JobDAO;
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
public final class CallbackJobs implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser) {
        String output;
        final long jobQuantity = jobDAO.count();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (jobQuantity != 0) {
            output = "Количество должностей в базе данных: " + jobQuantity;

            inlineKeyboardButtonList.add(buttonFactory.removeJob());
        } else {
            output = "Список должностей в базе данных пуст.";
        }

        inlineKeyboardButtonList.add(buttonFactory.addJob());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}