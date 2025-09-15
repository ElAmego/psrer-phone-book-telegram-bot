package by.psrer.userState.impl;

import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
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

import static by.psrer.entity.enums.UserState.BASIC;

@Service
@RequiredArgsConstructor
public final class AddJobSelection implements UserStateHandler {
    private final static int TEXT_MESSAGE_LIMIT = 255;
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        String output;

            if (textMessage.length() <= TEXT_MESSAGE_LIMIT) {
            final Job selectedJob = jobDAO.findByJobName(textMessage);

            if (selectedJob == null) {
                output = "Должность \"" + textMessage + "\" успешно добавлена в базу данных.";
                final Job newJob = Job.builder()
                        .jobName(textMessage)
                        .build();

                jobDAO.save(newJob);
                messageUtils.changeUserState(appUser, BASIC);
            } else {
                output = "Такая запись уже есть в базе данных. Введите заново или покиньте режим выбора.";
                inlineKeyboardButtonList.add(buttonFactory.cancel());
            }
        } else {
            output = "Вы превысили лимит сообщения в 255 символов. Введите заново или покиньте режим выбора.";
            inlineKeyboardButtonList.add(buttonFactory.cancel());
        }

        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}