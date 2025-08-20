package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.command.UserCommandContainer;
import by.psrer.entity.AppUser;
import org.springframework.stereotype.Service;

@Service
public final class CallbackHelp implements Callback {
    private final UserCommandContainer userCommandContainer;

    public CallbackHelp(final UserCommandContainer userCommandContainer) {
        this.userCommandContainer = userCommandContainer;
    }

    @Override
    public void execute(final AppUser appUser) {
        userCommandContainer.retrieveCommand("/help").execute(appUser);
    }
}