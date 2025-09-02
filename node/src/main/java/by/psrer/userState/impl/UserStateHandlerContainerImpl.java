package by.psrer.userState.impl;

import by.psrer.entity.enums.UserState;
import by.psrer.userState.UserStateHandler;
import by.psrer.userState.UserStateHandlerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static by.psrer.entity.enums.UserState.ADD_ADMIN_SELECTION;
import static by.psrer.entity.enums.UserState.AREA_SELECTION;
import static by.psrer.entity.enums.UserState.DEPARTMENT_SELECTION;
import static by.psrer.entity.enums.UserState.FIO_SELECTION;
import static by.psrer.entity.enums.UserState.REMOVE_ADMIN_SELECTION;

@Component
@SuppressWarnings("unused")
public final class UserStateHandlerContainerImpl implements UserStateHandlerContainer {
    private final Map<UserState, UserStateHandler> userStateHandlerMap;
    private final UserStateHandlerBasic userStateHandlerBasic;

    public UserStateHandlerContainerImpl(final UserStateHandlerBasic userStateHandlerBasic,
                                         final UserStateHandlerAreaSelection userStateHandlerAreaSelection,
                                         final UserStateHandlerDepartmentSelection userStateHandlerDepartmentSelection,
                                         final UserStateHandlerFioSelection userStateHandlerFioSelection,
                                         final UserStateHandlerAddAdminSelection userStateHandlerAddAdminSelection,
                                         final UserStateHandlerRemoveAdminSelection userStateHandlerRemoveAdminSelection) {
        userStateHandlerMap = new HashMap<>();
        userStateHandlerMap.put(AREA_SELECTION, userStateHandlerAreaSelection);
        userStateHandlerMap.put(DEPARTMENT_SELECTION, userStateHandlerDepartmentSelection);
        userStateHandlerMap.put(FIO_SELECTION, userStateHandlerFioSelection);
        userStateHandlerMap.put(ADD_ADMIN_SELECTION, userStateHandlerAddAdminSelection);
        userStateHandlerMap.put(REMOVE_ADMIN_SELECTION, userStateHandlerRemoveAdminSelection);

        this.userStateHandlerBasic = userStateHandlerBasic;
    }

    @Override
    public UserStateHandler retrieveUserStateHandler(final UserState userState) {
        return userStateHandlerMap.getOrDefault(userState, userStateHandlerBasic);
    }
}