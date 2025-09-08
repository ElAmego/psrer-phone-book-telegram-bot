package by.psrer.utils;

import by.psrer.entity.AppUser;
import by.psrer.entity.enums.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageUtils {
    void sendTextMessage(final Long chatId, final Answer answer);
    void sendReplacedTextMessage(final AppUser appUser, final Answer answer);
    AppUser findOrSaveAppUser(final Update update);
    void changeUserState(final AppUser appUser, final UserState userState);
    void changeUserStateWithIntermediateValue(final AppUser appUser, final UserState userState,
                                              final Long intermediateValue);
    void deleteUserMessage(final AppUser appUser, final Update update);
}