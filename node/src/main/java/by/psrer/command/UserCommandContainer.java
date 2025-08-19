package by.psrer.command;

public interface UserCommandContainer {
    Command retrieveCommand(final String commandIdentifier);
}