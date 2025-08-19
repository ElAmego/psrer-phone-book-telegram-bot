package by.psrer.command;

import by.psrer.entity.AppUser;

public interface Command {
    void execute(final AppUser appUser);
}