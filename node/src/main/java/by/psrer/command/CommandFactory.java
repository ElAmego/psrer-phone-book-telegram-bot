package by.psrer.command;

import by.psrer.entity.enums.Role;

import java.util.Optional;

public interface CommandFactory {
    Optional<Command> getCommand(final String cmd, final Role role);
}
