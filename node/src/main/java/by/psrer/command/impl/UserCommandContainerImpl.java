package by.psrer.command.impl;

import by.psrer.command.Command;
import by.psrer.command.CommandContainer;
import by.psrer.command.user.CommandHelp;
import by.psrer.command.user.CommandId;
import by.psrer.command.user.CommandPhones;
import by.psrer.command.user.CommandSearch;
import by.psrer.command.user.CommandStart;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public final class UserCommandContainerImpl implements CommandContainer {
    private final Map<String, Command> commandMap;

    public UserCommandContainerImpl(final CommandStart commandStart, final CommandHelp commandHelp,
                                    final CommandPhones commandPhones, final CommandSearch commandSearch,
                                    final CommandId commandId) {
        commandMap = new HashMap<>();
        commandMap.put("/start", commandStart);
        commandMap.put("/help", commandHelp);
        commandMap.put("/phones", commandPhones);
        commandMap.put("/search", commandSearch);
        commandMap.put("/id", commandId);
    }

    @Override
    public Command retrieveCommand(final String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, null);
    }
}