package by.psrer.utils.impl;

import by.psrer.utils.ButtonFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@SuppressWarnings("unused")
public final class ButtonFactoryImpl implements ButtonFactory {
    @Override
    public InlineKeyboardButton grantAccess() {
        return InlineKeyboardButton.builder()
                .text("Выдать доступ")
                .callbackData("grantAccessBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton revokeAccess() {
        return InlineKeyboardButton.builder()
                .text("Отозвать доступ")
                .callbackData("revokeAccessBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton mainMenu() {
        return InlineKeyboardButton.builder()
                .text("Главное меню")
                .callbackData("mainMenuBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton cancel() {
        return InlineKeyboardButton.builder()
                .text("Покинуть режим выбора")
                .callbackData("cancelBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton help() {
        return InlineKeyboardButton.builder()
                .text("Список команд")
                .callbackData("helpBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton addAdmin() {
        return InlineKeyboardButton.builder()
                .text("Добавить")
                .callbackData("addAdminBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton removeAdmin() {
        return InlineKeyboardButton.builder()
                .text("Убрать")
                .callbackData("removeAdminBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton addArea() {
        return InlineKeyboardButton.builder()
                .text("Добавить участок")
                .callbackData("addAreaBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton removeArea() {
        return InlineKeyboardButton.builder()
                .text("Удалить участок")
                .callbackData("removeAreaBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton dataManagement() {
        return InlineKeyboardButton.builder()
                .text("Управление данными")
                .callbackData("dataManagementBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton areas() {
        return InlineKeyboardButton.builder()
                .text("Участки")
                .callbackData("areasBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton departments() {
        return InlineKeyboardButton.builder()
                .text("Отделы")
                .callbackData("departmentsBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton jobs() {
        return InlineKeyboardButton.builder()
                .text("Должности")
                .callbackData("jobsBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton employees() {
        return InlineKeyboardButton.builder()
                .text("Сотрудники")
                .callbackData("employeesBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton addDepartment() {
        return InlineKeyboardButton.builder()
                .text("Добавить отдел")
                .callbackData("addDepartmentBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton removeDepartment() {
        return InlineKeyboardButton.builder()
                .text("Удалить отдел")
                .callbackData("removeDepartmentBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton access() {
        return InlineKeyboardButton.builder()
                .text("Доступ")
                .callbackData("accessBtn")
                .build();
    }

    @Override
    public InlineKeyboardButton admins() {
        return InlineKeyboardButton.builder()
                .text("Администраторы")
                .callbackData("adminsBtn")
                .build();
    }
}