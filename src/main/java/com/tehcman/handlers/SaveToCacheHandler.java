package com.tehcman.handlers;

import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SaveToCacheHandler implements Handler<Message> {
    private BuildMessageService buildMessageService;

    @Autowired
    public SaveToCacheHandler(@Lazy BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }


    @Override
    public void handle(Message message) {
        buildMessageService.buildButtons(message); //adding phone number button
    }
}
