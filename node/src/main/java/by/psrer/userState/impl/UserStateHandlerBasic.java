package by.psrer.userState.impl;

import by.psrer.command.UserCommandContainer;
import by.psrer.entity.AppUser;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

@Service
public final class UserStateHandlerBasic implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final UserCommandContainer userCommandContainer;

    public UserStateHandlerBasic(final MessageUtils messageUtils, final UserCommandContainer userCommandContainer) {
        this.messageUtils = messageUtils;
        this.userCommandContainer = userCommandContainer;
    }

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        if (textMessage.startsWith("/")) {
            final String cmd = textMessage.split(" ")[0].toLowerCase();
            userCommandContainer.retrieveCommand(cmd).execute(appUser);
        } else {
            messageUtils.sendTextMessage(appUser.getTelegramUserId(),
                    new Answer("Введенный вами текст не является командой.",
                            messageUtils.createHelpCommand()));
        }
    }
}