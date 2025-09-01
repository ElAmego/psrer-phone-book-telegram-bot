package by.psrer.command.impl;

import by.psrer.command.Command;
import by.psrer.command.CommandContainer;
import by.psrer.command.admin.CommandAdmin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public final class AdminCommandContainerImpl implements CommandContainer {
    private final Map<String, Command> commandMap;

    public AdminCommandContainerImpl(final CommandAdmin commandAdmin) {
        commandMap = new HashMap<>();
        commandMap.put("/admin", commandAdmin);
    }

    @Override
    public Command retrieveCommand(final String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, null);
    }
}