package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
public final class CommandId implements Command {
    private final MessageUtils messageUtils;

    public CommandId(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final String output = "Ваш телеграм id: " + chatId;

        messageUtils.sendTextMessage(chatId, new Answer(output, null));
    }
}