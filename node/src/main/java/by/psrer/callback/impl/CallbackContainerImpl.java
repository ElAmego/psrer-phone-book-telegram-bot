package by.psrer.callback.impl;

import by.psrer.callback.Callback;
import by.psrer.callback.CallbackContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@SuppressWarnings("unused")
public final class CallbackContainerImpl implements CallbackContainer {
    final Map<String, Callback> callbackMap;
    private final UnknownCallback unknownCallback;

    public CallbackContainerImpl(final UnknownCallback unknownCallback, final CallbackHelp callbackHelp,
                                 final CallbackCancel callbackCancel) {
        this.unknownCallback = unknownCallback;
        callbackMap = new HashMap<>();
        callbackMap.put("helpBtn", callbackHelp);
        callbackMap.put("cancelBtn", callbackCancel);
    }

    @Override
    public Callback retrieveCallback(final String callbackIdentifier) {
        return callbackMap.getOrDefault(callbackIdentifier, unknownCallback);
    }
}