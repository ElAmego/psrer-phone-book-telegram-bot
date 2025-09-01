package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.command.impl.UserCommandContainerImpl;
import by.psrer.entity.AppUser;
import org.springframework.stereotype.Service;

@Service
public final class CallbackHelp implements Callback {
    private final UserCommandContainerImpl userCommandContainer;

    public CallbackHelp(final UserCommandContainerImpl userCommandContainer) {
        this.userCommandContainer = userCommandContainer;
    }

    @Override
    public void execute(final AppUser appUser) {
        userCommandContainer.retrieveCommand("/help")
                .execute(appUser);
    }
}