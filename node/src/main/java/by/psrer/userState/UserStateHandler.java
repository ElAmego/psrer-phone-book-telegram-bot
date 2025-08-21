package by.psrer.userState;

import by.psrer.entity.AppUser;

public interface UserStateHandler {
    void execute(final AppUser appUser, final String textMessage);
}