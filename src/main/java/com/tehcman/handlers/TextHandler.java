package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class TextHandler implements Handler<Message> {
    private final MessageSender messageSender;
    private final BuildMessageService buildMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons
    private final BuildButtonsService buildButtonsService;

    private final Cache<User> userCache;


    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildMessageService buildMessageService, BuildInlineButtonsService buildInlineButtonsService, BuildButtonsService buildButtonsService, UserCache userCache) {
        this.messageSender = messageSender;
        this.buildMessageService = buildMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
        this.buildButtonsService = buildButtonsService;
        this.userCache = userCache;
    }


    @Override
    public void handle(Message message) {
        if (message.getText().equals("/start")) {
            buildButtonsService.beforeRegistrationButtons(message);
        } else if (message.getText().equals("I want a joke")) {
            var sendMessage = SendMessage.builder()
                    .text("Are you ready for my collection of the most hilarious jokes??\nIf so, press the button below!")
                    .chatId(message.getChatId().toString())
                    .build();

            sendMessage.setReplyMarkup(buildInlineButtonsService.build());
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb")) {
            var sendMsg = new SendMessage(message.getChatId().toString(), "no, you're dumb!");
            messageSender.messageSend(sendMsg);
        } else if (message.getText().equals("View my data")) {
            User userFromCache = userCache.findBy(message.getChatId());
//DUPLICATES
            var sendMessage = SendMessage.builder()
                    .parseMode("HTML")
                    .chatId(message.getChatId().toString())
                    .text(userFromCache.toString()).build();

            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("Remove my data")) {
            userCache.remove(message.getChatId());
            messageSender.messageSend(new SendMessage(message.getChatId().toString(), "All data about you has been removed"));

            buildButtonsService.beforeRegistrationButtons(message);

        } else {
            var sendMsg = new SendMessage(message.getChatId().toString(), "I did not understand you. Try to press/text something else");
            messageSender.messageSend(sendMsg);
        }
    }
}

