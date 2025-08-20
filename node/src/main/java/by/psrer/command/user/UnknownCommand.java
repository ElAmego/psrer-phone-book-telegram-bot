package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
public final class UnknownCommand implements Command {
    private final MessageUtils messageUtils;

    public UnknownCommand(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        messageUtils.sendTextMessage(chatId,
                new Answer("Вы ввели неизвестную команду.",
                        messageUtils.createHelpCommand()));
    }
}