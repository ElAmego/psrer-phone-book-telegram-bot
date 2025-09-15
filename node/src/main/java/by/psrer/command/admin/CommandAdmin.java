package by.psrer.command.admin;

import by.psrer.command.Command;
import by.psrer.dao.AreaDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.dao.EmployeeDAO;
import by.psrer.dao.JobDAO;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class CommandAdmin implements Command {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;
    private final AreaDAO areaDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final JobDAO jobDAO;

    @Override
    public void execute(final AppUser appUser) {
        Long chatId = appUser.getTelegramUserId();
        final StringBuilder output = new StringBuilder();
        long areaQuantity = areaDAO.count();
        long departmentQuantity = departmentDAO.count();
        long employeeQuantity = employeeDAO.count();
        long jobQuantity = jobDAO.count();

        output.append("Добро пожаловать в панель администратора ").append(appUser.getFirstName()).append(" ")
                .append(appUser.getLastName()).append(" !").append("\n\n").append("Ваш username: @")
                .append(appUser.getUsername()).append("\nВаш телеграм ID: ").append(appUser.getTelegramUserId());

        output.append("\n\nКоличество записей в базе данных:").append("\nУчастки: ").append(areaQuantity)
                .append("\nОтделы: ").append(departmentQuantity).append("\nСотрудники: ").append(employeeQuantity)
                .append("\nДолжности: ").append(jobQuantity);


        final List<InlineKeyboardButton> inlineKeyboardButtonList = createAdminButtons();

        messageUtils.sendTextMessage(chatId, new Answer(output.toString(), inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createAdminButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(buttonFactory.admins());
        inlineKeyboardButtonList.add(buttonFactory.access());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        return inlineKeyboardButtonList;
    }
}