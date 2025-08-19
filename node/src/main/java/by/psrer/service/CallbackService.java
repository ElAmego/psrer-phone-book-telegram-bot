package by.psrer.service;

import org.telegram.telegrambots.meta.api.objects.Update;

@SuppressWarnings("unused")
public interface CallbackService {
    void handleCallback(final Update update);
}