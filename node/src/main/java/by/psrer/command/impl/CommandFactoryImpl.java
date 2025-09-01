package by.psrer.command.impl;

import by.psrer.command.Command;
import by.psrer.command.CommandContainer;
import by.psrer.command.CommandFactory;
import by.psrer.entity.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.psrer.entity.enums.Role.ADMIN;

@Component
@SuppressWarnings("unused")
public class CommandFactoryImpl implements CommandFactory {
    private final List<CommandContainer> containers;

    public CommandFactoryImpl(final AdminCommandContainerImpl adminCommandContainer,
                              final UserCommandContainerImpl userCommandContainer) {
        this.containers = List.of(adminCommandContainer, userCommandContainer);
    }

    @Override
    public Optional<Command> getCommand(final String cmd, final Role role) {
        return containers.stream()
                .filter(container -> hasAccess(container, role))
                .map(container -> container.retrieveCommand(cmd))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private boolean hasAccess(final CommandContainer container, final Role role) {
        return !(container instanceof AdminCommandContainerImpl) || role == ADMIN;
    }
}