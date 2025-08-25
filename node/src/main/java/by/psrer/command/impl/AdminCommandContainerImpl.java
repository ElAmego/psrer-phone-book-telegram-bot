package by.psrer.command.impl;

import by.psrer.command.AdminCommandContainer;
import by.psrer.command.Command;
import by.psrer.command.admin.CommandAdmin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public class AdminCommandContainerImpl implements AdminCommandContainer {
    private final Map<String, Command> commandMap;

    public AdminCommandContainerImpl(final CommandAdmin commandAdmin) {
        commandMap = new HashMap<>();
        commandMap.put("/admin", commandAdmin);
    }

    @Override
    public Command retrieveCommand(final String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }
}