package by.psrer.userState.impl;

import by.psrer.entity.enums.UserState;
import by.psrer.userState.UserStateHandler;
import by.psrer.userState.UserStateHandlerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static by.psrer.entity.enums.UserState.ADD_ADMIN_SELECTION;
import static by.psrer.entity.enums.UserState.ADD_AREA_SELECTION;
import static by.psrer.entity.enums.UserState.ADD_DEPARTMENT_SELECTION_AREA;
import static by.psrer.entity.enums.UserState.ADD_DEPARTMENT_SELECTION_DEPARTMENT;
import static by.psrer.entity.enums.UserState.AREA_SELECTION;
import static by.psrer.entity.enums.UserState.DEPARTMENT_SELECTION;
import static by.psrer.entity.enums.UserState.FIO_SELECTION;
import static by.psrer.entity.enums.UserState.GRANT_ACCESS_SELECTION;
import static by.psrer.entity.enums.UserState.REMOVE_ADMIN_SELECTION;
import static by.psrer.entity.enums.UserState.REMOVE_AREA_SELECTION;
import static by.psrer.entity.enums.UserState.REMOVE_DEPARTMENT_SELECTION;
import static by.psrer.entity.enums.UserState.REVOKE_ACCESS_SELECTION;

@Component
@SuppressWarnings("unused")
public final class UserStateHandlerContainerImpl implements UserStateHandlerContainer {
    private final Map<UserState, UserStateHandler> userStateHandlerMap;
    private final Basic basic;

    public UserStateHandlerContainerImpl(final Basic basic, final AreaSelection areaSelection,
                                         final DepartmentSelection departmentSelection, final FioSelection fioSelection,
                                         final AddAdminSelection addAdminSelection,
                                         final RemoveAdminSelection removeAdminSelection,
                                         final GrantAccessSelection grantAccessSelection,
                                         final RevokeAccessSelection revokeAccessSelection,
                                         final AddAreaSelection addAreaSelection,
                                         final RemoveAreaSelection removeAreaSelection,
                                         final AddDepartmentSelectionArea addDepartmentSelectionArea,
                                         final AddDepartmentSelectionDepartment addDepartmentSelectionDepartment,
                                         final RemoveDepartmentSelection removeDepartmentSelection) {
        userStateHandlerMap = new HashMap<>();
        userStateHandlerMap.put(AREA_SELECTION, areaSelection);
        userStateHandlerMap.put(DEPARTMENT_SELECTION, departmentSelection);
        userStateHandlerMap.put(FIO_SELECTION, fioSelection);
        userStateHandlerMap.put(ADD_ADMIN_SELECTION, addAdminSelection);
        userStateHandlerMap.put(REMOVE_ADMIN_SELECTION, removeAdminSelection);
        userStateHandlerMap.put(GRANT_ACCESS_SELECTION, grantAccessSelection);
        userStateHandlerMap.put(REVOKE_ACCESS_SELECTION, revokeAccessSelection);
        userStateHandlerMap.put(ADD_AREA_SELECTION, addAreaSelection);
        userStateHandlerMap.put(REMOVE_AREA_SELECTION, removeAreaSelection);
        userStateHandlerMap.put(ADD_DEPARTMENT_SELECTION_AREA, addDepartmentSelectionArea);
        userStateHandlerMap.put(ADD_DEPARTMENT_SELECTION_DEPARTMENT, addDepartmentSelectionDepartment);
        userStateHandlerMap.put(REMOVE_DEPARTMENT_SELECTION, removeDepartmentSelection);

        this.basic = basic;
    }

    @Override
    public UserStateHandler retrieveUserStateHandler(final UserState userState) {
        return userStateHandlerMap.getOrDefault(userState, basic);
    }
}