package by.psrer.userState.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AreaDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.entity.Area;
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

import static by.psrer.entity.enums.UserState.ADD_DEPARTMENT_SELECTION_DEPARTMENT;

@Service
@RequiredArgsConstructor
public final class AddDepartmentSelectionArea implements UserStateHandler {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;
    private final AppUserConfigDAO appUserConfigDAO;

    @Override
    public void execute(final AppUser appUser, final String textMessage) {
        String output;
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        if (textMessage.matches("[-+]?\\d+")) {
            final int selectedAreaId = Integer.parseInt(textMessage);
            final Optional<Area> selectedArea = areaDAO.findNthSafely(selectedAreaId);

            if (selectedArea.isPresent()) {
                final Area area = selectedArea.get();
                final Long areaId = area.getAreaId();
                final AppUserConfig appUserConfig = appUser.getAppUserConfigId();

                appUserConfig.getIntermediateData().put("areaId", areaId);
                appUserConfigDAO.save(appUserConfig);
                messageUtils.changeUserState(appUser, ADD_DEPARTMENT_SELECTION_DEPARTMENT);
                output = "Введите название отдела: ";
            } else {
                output = "В списке нет выбранного вами значения. Введите корректное значение или покиньте режим выбора.";
            }
        } else {
            output = "Введенное вами значение не является цифрой. Введите корректное значение или покиньте режим " +
                    "выбора.";
        }

        inlineKeyboardButtonList.add(buttonFactory.cancel());
        messageUtils.sendReplacedTextMessage(appUser, new Answer(output, inlineKeyboardButtonList));
    }
}