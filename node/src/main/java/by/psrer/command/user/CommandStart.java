package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@Log4j
public final class CommandStart implements Command {
    private final MessageUtils messageUtils;

    public CommandStart(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        messageUtils.sendTextMessage(chatId, new Answer("Executing start command", null));
    }
}