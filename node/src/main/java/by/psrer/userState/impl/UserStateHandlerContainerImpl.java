package by.psrer.userState.impl;

import by.psrer.entity.enums.UserState;
import by.psrer.userState.UserStateHandler;
import by.psrer.userState.UserStateHandlerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static by.psrer.entity.enums.UserState.AREA_SELECTION;
import static by.psrer.entity.enums.UserState.DEPARTMENT_SELECTION;

@Service
@SuppressWarnings("unused")
public final class UserStateHandlerContainerImpl implements UserStateHandlerContainer {
    private final Map<UserState, UserStateHandler> userStateHandlerMap;
    private final UserStateHandlerBasic userStateHandlerBasic;

    public UserStateHandlerContainerImpl(final UserStateHandlerBasic userStateHandlerBasic,
                                         final UserStateHandlerAreaSelection userStateHandlerAreaSelection,
                                         final UserStateHandlerDepartmentSelection userStateHandlerDepartmentSelection) {
        userStateHandlerMap = new HashMap<>();
        userStateHandlerMap.put(AREA_SELECTION, userStateHandlerAreaSelection);
        userStateHandlerMap.put(DEPARTMENT_SELECTION, userStateHandlerDepartmentSelection);

        this.userStateHandlerBasic = userStateHandlerBasic;
    }

    @Override
    public UserStateHandler retrieveUserStateHandler(final UserState userState) {
        return userStateHandlerMap.getOrDefault(userState, userStateHandlerBasic);
    }
}