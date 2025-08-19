package by.psrer.command.impl;

import by.psrer.command.Command;
import by.psrer.command.UserCommandContainer;
import by.psrer.command.user.CommandStart;
import by.psrer.command.user.UnknownCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public final class UserCommandContainerImpl implements UserCommandContainer {
    private final Map<String, Command> commandMap;
    private final UnknownCommand unknownCommand;

    public UserCommandContainerImpl(final CommandStart commandStart, final UnknownCommand unknownCommand) {
        commandMap = new HashMap<>();
        commandMap.put("/start", commandStart);
        this.unknownCommand = unknownCommand;
    }

    @Override
    public Command retrieveCommand(final String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}