package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.psrer.entity.enums.UserState.AREA_SELECTION;

@Service
@RequiredArgsConstructor
public final class CommandPhones implements Command {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();
        final StringBuilder output = new StringBuilder();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();


        if (areaList.isEmpty()) {
            output.append("Список участков пуст.");
        } else {
            inlineKeyboardButtonList.add(buttonFactory.cancel());
            output.append("""
                        Вы перешли в режим выбора. Для выбора необходимого участка отправьте соответствующую цифру. \
                        Нажмите на кнопку "Покинуть режим выбора" чтобы выйти из режима выбора.
                        
                        Список участков:
                        """);

            int inc = 0;

            for (final Area areaFromList: areaList) {
                output.append(++inc).append(": ").append(areaFromList.getAreaName()).append("\n");
            }

            messageUtils.changeUserState(appUser, AREA_SELECTION);
        }

        messageUtils.sendTextMessage(chatId, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}