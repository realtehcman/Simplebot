package com.tehcman.handlers;

import com.tehcman.entities.Position;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(),
                message.getFrom().getUserName(), Position.PHONE_NUMBER);
        buildMessageService.addingPhoneNumberButton(message); //adding phone number button
        return newUser;
    }

    private void registerRestUserData(User user, Message message) {
        switch (user.getPosition()) {
            case PHONE_NUMBER: //phase 1
                if (user.getId()!=null) {
                    //
                }
                else{
                    throw new NullPointerException("User's id (chat id) wasn't found");
                }
/*                if (message.getText().equals())


            case AGE:

            case NONE:*/

        }
    }


    @Override
    public void handle(Message message) {
        //if no user is found in the registry(cache), start a new user registration
        if (userCache.findBy(message.getChatId()) == null) {
            registerRestUserData(generateDefaultUserInformationFromMessage(message), message);
        } else {
            messageSender.messageSend(new SendMessage(message.getChatId().toString(), "Hey. You are already in the system." +
                    " Instead of duplicating data of yourself, do something useful in your life"));
        }


    }
}
