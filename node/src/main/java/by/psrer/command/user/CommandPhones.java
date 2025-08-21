package by.psrer.command.user;

import by.psrer.command.Command;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.psrer.entity.enums.UserState.AREA_SELECTION;

@Service
public final class CommandPhones implements Command {
    private final AreaDAO areaDAO;
    private final MessageUtils messageUtils;

    public CommandPhones(final MessageUtils messageUtils, final AreaDAO areaDAO) {
        this.messageUtils = messageUtils;
        this.areaDAO = areaDAO;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        final List<Area> areaList = areaDAO.findAllByOrderByAreaIdAsc();
        final StringBuilder output = new StringBuilder();

        if (areaList.isEmpty()) {
            output.append("Список участков пуст.");
        } else {
            output.append("""
                        Вы перешли в режим выбора. Для выбора необходимого участка отправьте соответствующую цифру. \
                        Нажмите на кнопку "Выйти из режима" чтобы покинуть режим выбора.
                        
                        Список участков:
                        """);

            int inc = 0;

            for (final Area areaFromList: areaList) {
                output.append(inc++).append(": ").append(areaFromList.getAreaName()).append("\n");
            }

            messageUtils.changeUserState(appUser, AREA_SELECTION);
        }

        messageUtils.sendTextMessage(chatId, new Answer(output.toString(), null));
    }
}