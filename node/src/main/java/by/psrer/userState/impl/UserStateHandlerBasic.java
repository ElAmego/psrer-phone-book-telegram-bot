package by.psrer.userState.impl;

import by.psrer.command.AdminCommandContainer;
import by.psrer.command.UserCommandContainer;
import by.psrer.entity.AppUser;
import by.psrer.entity.enums.Role;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

import static by.psrer.entity.enums.Role.ADMIN;

@Service
public final class UserStateHandlerBasic implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final UserCommandContainer userCommandContainer;
    private final AdminCommandContainer adminCommandContainer;

    public UserStateHandlerBasic(final MessageUtils messageUtils, final UserCommandContainer userCommandContainer,
                                 final AdminCommandContainer adminCommandContainer) {
        this.messageUtils = messageUtils;
        this.userCommandContainer = userCommandContainer;
        this.adminCommandContainer = adminCommandContainer;
    }

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        if (textMessage.startsWith("/")) {
            final Role userRole = appUser.getAppUserConfigId().getRole();
            final String cmd = textMessage.split(" ")[0].toLowerCase();
            userCommandContainer.retrieveCommand(cmd).execute(appUser);

            if (userRole == ADMIN) {
                adminCommandContainer.retrieveCommand(cmd).execute(appUser);
            }
        } else {
            messageUtils.sendTextMessage(appUser.getTelegramUserId(),
                    new Answer("Введенный вами текст не является командой.",
                            messageUtils.createHelpCommand()));
        }
    }
}