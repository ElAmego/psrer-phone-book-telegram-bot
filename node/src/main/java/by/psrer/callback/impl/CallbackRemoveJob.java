package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Job;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.REMOVE_JOB_SELECTION;

@Service
@RequiredArgsConstructor
public final class CallbackRemoveJob implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final List<Job> jobList = jobDAO.findAllByOrderByJobIdAsc();
        final StringBuilder output = new StringBuilder();

        output.append("Введите номер должности из списка, который вы хотите удалить из базы данных (Например: 1):\n");

        int inc = 0;
        for (final Job job: jobList) {
            output.append("\n").append(++inc).append(": ").append(job.getJobName());
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, REMOVE_JOB_SELECTION);
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}