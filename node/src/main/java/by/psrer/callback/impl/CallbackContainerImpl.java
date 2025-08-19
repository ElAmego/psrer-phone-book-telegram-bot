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

    public CallbackContainerImpl(final UnknownCallback unknownCallback) {
        this.unknownCallback = unknownCallback;
        callbackMap = new HashMap<>();
    }

    @Override
    public Callback retrieveCallback(final String callbackIdentifier) {
        return callbackMap.getOrDefault(callbackIdentifier, unknownCallback);
    }
}