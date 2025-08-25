package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public final class CommandStart implements Command {
    private final MessageUtils messageUtils;

    public CommandStart(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = messageUtils.createHelpCommand();

        final String startMessage = """
                Добро пожаловать в телефонный справочник ПГРЭЗ.
                
                Контакты администратора для вопросов или обновления базы данных:
                +37529*******
                @admin_user
                """;

        messageUtils.sendTextMessage(chatId, new Answer(startMessage, inlineKeyboardButtonList));
    }
}