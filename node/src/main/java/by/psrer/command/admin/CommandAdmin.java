package by.psrer.command.admin;

import by.psrer.command.Command;
import by.psrer.entity.AppUser;
import by.psrer.utils.Answer;
import by.psrer.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public final class CommandAdmin implements Command {
    private final MessageUtils messageUtils;

    public CommandAdmin(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
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
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Администраторы")
                .callbackData("adminsBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Доступ")
                .callbackData("accessBtn")
                .build());

        inlineKeyboardButtonList.add(InlineKeyboardButton.builder()
                .text("Добавление данных")
                .callbackData("addDataBtn")
                .build());

        return inlineKeyboardButtonList;
    }
}