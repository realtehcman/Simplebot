package com.tehcman.handlers;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackQueryHandler implements Handler {
    private final MessageSender messageSender;

    public CallBackQueryHandler(@Lazy MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Update t) {
        CallbackQuery inlineButtonPressed = t.getCallbackQuery();

        if(inlineButtonPressed.getData().equals("next_action")){
                var sendMessage = SendMessage.builder()
                        .text("*next joke*")
                        .chatId(t.getMessage().getChatId().toString())
                        .build();

                var keyboardMarkup = new InlineKeyboardMarkup();

                InlineKeyboardButton prevAction = InlineKeyboardButton.builder()
                        .text("Previous joke").callbackData("prev_action").build();

                InlineKeyboardButton nextAction = InlineKeyboardButton.builder()
                        .text("Next joke").callbackData("next_action").build();

                List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
                ArrayList<InlineKeyboardButton> row1 = new ArrayList();
                row1.add(prevAction);
                row1.add(nextAction);
                listOfInlineButtons.add(row1);

                sendMessage.setReplyMarkup(keyboardMarkup);

                keyboardMarkup.setKeyboard(listOfInlineButtons);
                messageSender.messageSend(sendMessage);
        }
    }
}
