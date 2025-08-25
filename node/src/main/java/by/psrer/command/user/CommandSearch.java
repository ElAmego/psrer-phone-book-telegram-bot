package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static by.psrer.entity.enums.UserState.FIO_SELECTION;

@Service
public final class CommandSearch implements Command {
    private final MessageUtils messageUtils;

    public CommandSearch(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = messageUtils.createCancelCommand();
        final String output = """
                Вы перешли в режим поиска по ФИО. Нажмите на кнопку "Покинуть режим выбора" чтобы выйти из режима \
                выбора.
                
                Введите подстроку ФИО для поиска:
                """;

        messageUtils.changeUserState(appUser, FIO_SELECTION);
        messageUtils.sendTextMessage(chatId, new Answer(output, inlineKeyboardButtonList));
    }
}