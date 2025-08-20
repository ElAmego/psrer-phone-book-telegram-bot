package by.psrer.utils;

import by.psrer.entity.AppUser;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface MessageUtils {
    void sendTextMessage(final Long chatId, final Answer answer);
    AppUser findOrSaveAppUser(final Update update);
    List<InlineKeyboardButton> createHelpCommand();
}