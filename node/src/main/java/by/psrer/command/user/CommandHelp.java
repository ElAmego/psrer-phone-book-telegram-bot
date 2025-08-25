package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
public final class CommandHelp implements Command {
    private final MessageUtils messageUtils;

    public CommandHelp(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();

        final String output = """
                Список доступных команд:
                /start – Стартовая страница бота.
                /phones – Телефонная книга.
                /search – Поиск номера по подстроке ФИО.
                /help – Список доступных команд.
                """;

        messageUtils.sendTextMessage(chatId, new Answer(output, null));
    }
}
