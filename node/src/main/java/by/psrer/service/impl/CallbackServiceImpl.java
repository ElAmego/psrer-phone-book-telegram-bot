package by.psrer.service.impl;

import by.psrer.callback.CallbackContainer;
import by.psrer.entity.AppUser;
import by.psrer.service.CallbackService;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@SuppressWarnings("unused")
public final class CallbackServiceImpl implements CallbackService {
    private final MessageUtils messageUtils;
    private final CallbackContainer callbackContainer;

    public CallbackServiceImpl(final MessageUtils messageUtils, final CallbackContainer callbackContainer) {
        this.messageUtils = messageUtils;
        this.callbackContainer = callbackContainer;
    }

    @Override
    public void handleCallback(final Update update) {
        final AppUser appUser = messageUtils.findOrSaveAppUser(update);
        final CallbackQuery callbackQuery = update.getCallbackQuery();

        processCallback(appUser, callbackQuery);
    }

    private void processCallback(final AppUser appUser, final CallbackQuery callbackQuery) {
        final String action = callbackQuery.getData();
        callbackContainer.retrieveCallback(action).execute(appUser);
    }
}