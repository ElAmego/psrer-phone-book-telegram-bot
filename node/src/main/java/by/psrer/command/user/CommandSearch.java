package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.FIO_SELECTION;

@Service
public final class CommandSearch implements Command {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CommandSearch(final MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        final String output = "Введите подстроку ФИО для поиска (Например: Иванов или ив): ";

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.changeUserState(appUser, FIO_SELECTION);
        messageUtils.sendTextMessage(chatId, new Answer(output, inlineKeyboardButtonList));
    }
}