package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildSendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class TextHandler implements Handler<Message> {
    private final MessageSender messageSender;
    private final BuildSendMessageService buildSendMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons
    private final BuildButtonsService buildButtonsService;

    private final Cache<User> userCache;

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildSendMessageService buildSendMessageService, BuildInlineButtonsService buildInlineButtonsService, @Lazy BuildButtonsService buildButtonsService, UserCache userCache) {
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
        this.buildButtonsService = buildButtonsService;
        this.userCache = userCache;
    }

    //TODO SHOULD I KEEP THE FOLLOWING CODE?
    //after pressing a button the user will receive a message
    public String chooseMsgForUser(Message message) {
        String messageToTheUser;
        switch (message.getText()) {
            case "/start":
                messageToTheUser = "Yay! You've just launched this bot!";
                break;
            case "Temporary save my info into the cache":
                messageToTheUser = "Press button Phone Number";
                break;
            default:
                messageToTheUser = "ok";
        }
        return messageToTheUser;
    }


    @Override
    public void handle(Message message) {
        if (message.getText().equals("/start")) {
            buildSendMessageService.createHTMLMessage(message.getChatId().toString(), message.getText(), buildButtonsService.getMainMarkup())
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