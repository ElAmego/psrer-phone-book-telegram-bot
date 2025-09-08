package by.psrer.userState.impl;

import by.psrer.dao.AreaDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.Area;
import by.psrer.entity.Department;
import by.psrer.userState.UserStateHandler;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.psrer.entity.enums.UserState.BASIC;
import static by.psrer.entity.enums.UserState.DEPARTMENT_SELECTION;

@Service
@RequiredArgsConstructor
public final class AreaSelection implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;
    private final DepartmentDAO departmentDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final Long chatId = appUser.getTelegramUserId();
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedAreaId = Integer.parseInt(textMessage);
            final Optional<Area> selectedArea = areaDAO.findNthSafely(selectedAreaId);

            if (selectedArea.isPresent()) {
                final Long areaId = selectedArea.get().getAreaId();
                final List<Department> departmentList = departmentDAO.findByArea_AreaId(areaId);

                if (!departmentList.isEmpty()) {
                    output.append("""
                    Вы перешли в режим выбора. Для выбора необходимого отдела отправьте соответствующую цифру. \
                    Нажмите на кнопку "Покинуть режим выбора" чтобы выйти из режима выбора.
                
                    Список отделов:
                    """);

                    int inc = 0;

                    for (final Department departmentFromList: departmentList) {
                        output.append(++inc).append(": ")
                                .append(departmentFromList.getDepartmentName()).append("\n");
                    }

                    messageUtils.changeUserStateWithIntermediateValue(appUser, DEPARTMENT_SELECTION, areaId);
                } else {
                    output.append("Список отделов текущего участка пуст. Вы вышли из режима выбора.");
                    messageUtils.changeUserState(appUser, BASIC);
                }
            } else {
                output.append("""
                    В списке нет выбранного вами значения. Введите корректное значение или нажмите на кнопку \
                    "Покинуть режим выбора"
                    """);
            }
        } else {
            output.append("""
                    Введенное вами значение не является цифрой. Введите корректное значение или нажмите на кнопку \
                    "Покинуть режим выбора".
                    """);
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.sendTextMessage(chatId, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}