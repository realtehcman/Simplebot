package com.tehcman.handlers;

import com.tehcman.entities.Position;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SaveToCacheHandler implements Handler<Message> {
    private BuildMessageService buildMessageService;
    private final Cache<User> userCache;
    private MessageSender messageSender;

    @Autowired
    public SaveToCacheHandler(@Lazy BuildMessageService buildMessageService, Cache<User> userCache, MessageSender messageSender) {
        this.buildMessageService = buildMessageService;
        this.userCache = userCache;
        this.messageSender = messageSender;
    }

    private User generateUserFromMessage(Message message){
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(), message.getFrom().getUserName(), Position.PHONE_NUMBER);
        return newUser;
    }


    @Override
    public void handle(Message message) {



        buildMessageService.buildButtons(message); //adding phone number button


    }
}
