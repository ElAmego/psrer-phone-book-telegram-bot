package by.psrer.utils;

import by.psrer.entity.AppUser;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageUtils {
    void sendTextMessage(final Long chatId, final Answer answer);
    AppUser findOrSaveAppUser(final Update update);
}