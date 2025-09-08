package by.psrer.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface ButtonFactory {
    InlineKeyboardButton grantAccess();
    InlineKeyboardButton revokeAccess();
    InlineKeyboardButton mainMenu();
    InlineKeyboardButton cancel();
    InlineKeyboardButton help();
    InlineKeyboardButton addAdmin();
    InlineKeyboardButton removeAdmin();
    InlineKeyboardButton addArea();
    InlineKeyboardButton removeArea();
    InlineKeyboardButton dataManagement();
    InlineKeyboardButton areas();
    InlineKeyboardButton departments();
    InlineKeyboardButton jobs();
    InlineKeyboardButton employees();
    InlineKeyboardButton addDepartment();
    InlineKeyboardButton removeDepartment();
    InlineKeyboardButton access();
    InlineKeyboardButton admins();
}