package by.psrer.userState.impl;

import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Job;
import by.psrer.service.JobService;
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
public final class RemoveJobSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final JobDAO jobDAO;
    private final JobService jobService;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedJobId = Integer.parseInt(textMessage);
            final Optional<Job> selectedJob = jobDAO.findNthSafely(selectedJobId);

            if (selectedJob.isPresent()) {
                final Job job = selectedJob.get();
                final Long jobId = job.getJobId();

                jobService.deleteJobWithEmployees(jobId);
                output = "Должность \"" + job.getJobName() + "\" успешно удалена из базы данных.";
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "В списке нет выбранного вами значения. Введите корректное значение или покиньте режим выбора.";
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }
        } else {
            output = "Введенное вами значение не является цифрой. Введите корректное значение или покиньте режим " +
                    "выбора.";
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}