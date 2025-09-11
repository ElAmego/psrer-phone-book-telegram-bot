package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.callback.CallbackContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public final class CallbackContainerImpl implements CallbackContainer {
    final Map<String, Callback> callbackMap;
    private final UnknownCallback unknownCallback;

    public CallbackContainerImpl(final UnknownCallback unknownCallback, final CallbackHelp callbackHelp,
                                 final CallbackCancel callbackCancel, final CallbackAdmins callbackAdmins,
                                 final CallbackMainMenu callbackMainMenu, final CallbackAddAdmin callbackAddAdmin,
                                 final CallbackRemoveAdmin callbackRemoveAdmin, final CallbackAccess callbackAccess,
                                 final CallbackGrantAccess callbackGrantAccess, final CallbackAreas callbackAreas,
                                 final CallbackRevokeAccess callbackRevokeAccess, final CallbackAddArea callbackAddArea,
                                 final CallbackDataManagement callbackDataManagement, final CallbackJobs callbackJobs,
                                 final CallbackRemoveArea callbackRemoveArea, final CallbackAddJob callbackAddJob,
                                 final CallbackDepartments callbackDepartments, final CallbackRemoveJob callbackRemoveJob,
                                 final CallbackAddDepartment callbackAddDepartment,
                                 final CallbackEmployees callbackEmployees,
                                 final CallbackRemoveDepartment callbackRemoveDepartment,
                                 final CallbackAddEmployee callbackAddEmployee,
                                 final CallbackRemoveEmployee callbackRemoveEmployee) {
        this.unknownCallback = unknownCallback;
        callbackMap = new HashMap<>();
        callbackMap.put("helpBtn", callbackHelp);
        callbackMap.put("cancelBtn", callbackCancel);
        callbackMap.put("adminsBtn", callbackAdmins);
        callbackMap.put("mainMenuBtn", callbackMainMenu);
        callbackMap.put("addAdminBtn", callbackAddAdmin);
        callbackMap.put("removeAdminBtn", callbackRemoveAdmin);
        callbackMap.put("accessBtn", callbackAccess);
        callbackMap.put("grantAccessBtn", callbackGrantAccess);
        callbackMap.put("revokeAccessBtn", callbackRevokeAccess);
        callbackMap.put("dataManagementBtn", callbackDataManagement);
        callbackMap.put("areasBtn", callbackAreas);
        callbackMap.put("addAreaBtn", callbackAddArea);
        callbackMap.put("removeAreaBtn", callbackRemoveArea);
        callbackMap.put("departmentsBtn", callbackDepartments);
        callbackMap.put("addDepartmentBtn", callbackAddDepartment);
        callbackMap.put("removeDepartmentBtn", callbackRemoveDepartment);
        callbackMap.put("jobsBtn", callbackJobs);
        callbackMap.put("addJobBtn", callbackAddJob);
        callbackMap.put("removeJobBtn", callbackRemoveJob);
        callbackMap.put("employeesBtn",callbackEmployees);
        callbackMap.put("addEmployeeBtn",callbackAddEmployee);
        callbackMap.put("removeEmployeeBtn",callbackRemoveEmployee);
    }

    @Override
    public Callback retrieveCallback(final String callbackIdentifier) {
        return callbackMap.getOrDefault(callbackIdentifier, unknownCallback);
    }
}