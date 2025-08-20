package by.psrer.service.impl;

import by.psrer.callback.CallbackContainer;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.service.CallbackService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@SuppressWarnings("unused")
public final class CallbackServiceImpl implements CallbackService {
    private final CallbackContainer callbackContainer;
    private final AppUserDAO appUserDAO;

    public CallbackServiceImpl(final CallbackContainer callbackContainer, final AppUserDAO appUserDAO) {
        this.callbackContainer = callbackContainer;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void handleCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long chatId = callbackQuery.getMessage().getChatId();
        final AppUser appUser = appUserDAO.findAppUserByTelegramUserId(chatId);
        processCallback(appUser, callbackQuery);
    }

    private void processCallback(final AppUser appUser, final CallbackQuery callbackQuery) {
        final String action = callbackQuery.getData();
        callbackContainer.retrieveCallback(action).execute(appUser);
    }
}