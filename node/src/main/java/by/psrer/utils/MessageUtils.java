package by.psrer.utils;

import by.psrer.entity.AppUser;
import by.psrer.entity.enums.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface MessageUtils {
    void sendTextMessage(final Long chatId, final Answer answer);
    AppUser findOrSaveAppUser(final Update update);
    List<InlineKeyboardButton> createHelpCommand();
    List<InlineKeyboardButton> createCancelCommand();
    void changeUserState(final AppUser appUser, final UserState userState);
    void changeUserStateWithIntermediateValue(final AppUser appUser, final UserState userState,
                                              final Long intermediateValue);
    void deleteUserMessage(final AppUser appUser, final Update update);
}