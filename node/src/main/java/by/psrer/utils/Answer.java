package by.psrer.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public record Answer(String answerText, List<InlineKeyboardButton> inlineKeyboardButtonList) {}