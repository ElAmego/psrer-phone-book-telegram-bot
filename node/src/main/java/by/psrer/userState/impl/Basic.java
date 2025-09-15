package by.psrer.userState.impl;

import by.psrer.command.Command;
import by.psrer.command.CommandFactory;
import by.psrer.entity.AppUser;
import by.psrer.entity.enums.Role;
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

@Service
@RequiredArgsConstructor
public final class Basic implements UserStateHandler {
    private final static String NOT_A_COMMAND_TEXT = "Введенный вами текст не является командой.";
    private final static String UNKNOWN_COMMAND_TEXT = "Такой команды не существует.";
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final CommandFactory commandFactory;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        if (textMessage.startsWith("/")) {
            final Role userRole = appUser.getAppUserConfigId().getRole();
            final String cmd = textMessage.split(" ")[0].toLowerCase();
            final Optional<Command> command = commandFactory.getCommand(cmd, userRole);

            if (command.isPresent()) {
                command.get().execute(appUser);
            } else {
                sendErrorMessage(appUser, UNKNOWN_COMMAND_TEXT);
            }
        } else {
            sendErrorMessage(appUser, NOT_A_COMMAND_TEXT);
        }
    }

    private void sendErrorMessage(final AppUser appUser, final String errorMessage) {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        inlineKeyboardButtonList.add(buttonFactory.help());
        messageUtils.sendTextMessage(appUser.getTelegramUserId(),
                new Answer(errorMessage, inlineKeyboardButtonList));
    }
}