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

@Service
@RequiredArgsConstructor
public final class CallbackJobs implements Callback {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser) {
        final StringBuilder output = new StringBuilder();
        final List<Job> jobList = jobDAO.findAllByOrderByJobIdAsc();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (!jobList.isEmpty()) {
            output.append("Список должностей: ");
            int inc = 0;

            for (final Job job: jobList) {
                output.append("\n").append(++inc).append(": ").append(job.getJobName());
            }

            inlineKeyboardButtonList.add(buttonFactory.removeJob());
        } else {
            output.append("Список должностей в базе данных пуст.");
        }

        inlineKeyboardButtonList.add(buttonFactory.addJob());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}