package by.psrer.command;

public interface CommandContainer {
    Command retrieveCommand(final String commandIdentifier);
}