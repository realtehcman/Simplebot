package com.tehcman.handlers;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackQueryHandler implements Handler<CallbackQuery> {
    private final MessageSender messageSender;

    public CallBackQueryHandler(@Lazy MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void handle(CallbackQuery t) {
        CallbackQuery inlineButtonPressed = t;

        if (inlineButtonPressed.getData().equals("next_action")) {
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
            keyboardMarkup.setKeyboard(listOfInlineButtons);

            var editMessageText = EditMessageText.builder()
                    .text("*testing, next joke*")
                    .chatId(t.getMessage().getChatId().toString())
                    .messageId(t.getMessage().getMessageId())
                    .replyMarkup(keyboardMarkup)
                    .build();

            editMessageText.setReplyMarkup(keyboardMarkup);

            messageSender.editMessageSend(editMessageText);
        }
    }
}


