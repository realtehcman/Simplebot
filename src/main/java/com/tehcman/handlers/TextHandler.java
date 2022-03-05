package com.tehcman.handlers;

import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextHandler implements Handler<Message> {
    private final MessageSender messageSender;
    private final BuildMessageService buildMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildMessageService buildMessageService, BuildInlineButtonsService buildInlineButtonsService) {
        this.messageSender = messageSender;
        this.buildMessageService = buildMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
    }


    @Override
    public void handle(Message message) {
        if (message.getText().equals("give me the text")) {
            var sendMessage = SendMessage.builder()
                    .text("" +
                            "Two Russian invaders are out in the woods in Ukraine when one of them collapses.\n" +
                            "He doesn't seem to be breathing and his eyes are glazed.\n" +
                            "The other guy whips out his phone and calls the Russian emergency services.\n" +
                            "He gasps, \"My comrade is dead! What can I do?\".\n" +
                            "The operator says \"Calm down. I can help. First,\n" +
                            "let's make sure he's dead.\" There is a silence, then a shot is heard.\n" +
                            "Back on the phone, the guy says \"OK, now what?\"\n")
                    .chatId(message.getChatId().toString())
                    .build();

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

            sendMessage.setReplyMarkup(keyboardMarkup);
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb 2")) {
            var sendMsg = new SendMessage(message.getChatId().toString(), "no, you're dumb!");
            messageSender.messageSend(sendMsg);
        }
    }
}

