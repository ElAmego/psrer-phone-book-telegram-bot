package by.psrer.userState.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AreaDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
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
    private final AppUserConfigDAO appUserConfigDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        final StringBuilder output = new StringBuilder();
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedAreaId = Integer.parseInt(textMessage);
            final Optional<Area> selectedArea = areaDAO.findNthSafely(selectedAreaId);

            if (selectedArea.isPresent()) {
                final Long areaId = selectedArea.get().getAreaId();
                final List<Department> departmentList = departmentDAO.findByArea_AreaId(areaId);

                if (!departmentList.isEmpty()) {
                    final AppUserConfig appUserConfig = appUser.getAppUserConfigId();

                    output.append("Введите номер необходимого отдела из списка (Например: 1):\n");

                    int inc = 0;
                    for (final Department departmentFromList: departmentList) {
                        output.append("\n").append(++inc).append(": ").append(departmentFromList.getDepartmentName());
                    }

                    appUserConfig.getIntermediateData().put("areaId", areaId);
                    appUserConfigDAO.save(appUserConfig);
                    messageUtils.changeUserState(appUser, DEPARTMENT_SELECTION);
                } else {
                    output.append("Список отделов текущего участка пуст. Вы покинули режима выбора.");
                    messageUtils.changeUserState(appUser, BASIC);
                }
            } else {
                output.append("В списке нет выбранного вами значения. Введите корректное значение или покиньте режим " +
                        "выбора");
            }
        } else {
            output.append("Введенное вами значение не является цифрой. Введите корректное значение или покиньте " +
                    "режим выбора");
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output.toString(), inlineKeyboardButtonList));
    }
}