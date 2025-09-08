package by.psrer.command.admin;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.ButtonFactory;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public final class CommandAdmin implements Command {
    private final MessageUtils messageUtils;
    private final ButtonFactory buttonFactory;

    public CommandAdmin(final MessageUtils messageUtils, final ButtonFactory buttonFactory) {
        this.messageUtils = messageUtils;
        this.buttonFactory = buttonFactory;
    }

    @Override
    public void execute(final AppUser appUser) {
        final Long chatId = appUser.getTelegramUserId();
        String output = "Добро пожаловать в панель администратора " + appUser.getFirstName() + " " +
                appUser.getLastName() + " !" + "\n\n" + "Ваш username: @" + appUser.getUsername() +
                "\nВаш телеграм ID: " + chatId;
        final List<InlineKeyboardButton> inlineKeyboardButtonList = createAdminButtons();

        messageUtils.sendTextMessage(chatId, new Answer(output, inlineKeyboardButtonList));
    }

    private List<InlineKeyboardButton> createAdminButtons() {
        final List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(buttonFactory.admins());
        inlineKeyboardButtonList.add(buttonFactory.access());
        inlineKeyboardButtonList.add(buttonFactory.dataManagement());
        return inlineKeyboardButtonList;
    }
}