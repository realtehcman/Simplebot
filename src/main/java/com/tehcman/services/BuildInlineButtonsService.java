package com.tehcman.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildInlineButtonsService implements ReplyKeyboard {

    public InlineKeyboardMarkup build() {
        var keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton prevAction = InlineKeyboardButton.builder()
                .text("Previous joke").callbackData("prev_action").build();

        InlineKeyboardButton nextAction = InlineKeyboardButton.builder()
                .text("Next joke").callbackData("next_action").build();

        List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(prevAction);
        row1.add(nextAction);
        listOfInlineButtons.add(row1);

        keyboardMarkup.setKeyboard(listOfInlineButtons);
        return keyboardMarkup;
    }


    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
