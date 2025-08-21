package by.psrer.userState;

import by.psrer.entity.enums.UserState;

public interface UserStateHandlerContainer {
    UserStateHandler retrieveUserStateHandler(final UserState userState);
}