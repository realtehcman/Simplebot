package com.tehcman.handlers;

import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartHandler implements Handler{
    private BuildMessageService buildMessageService;

    @Autowired
    public StartHandler(@Lazy BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }

    @Override
    public void handle(Update t) {
        if (t.getMessage().getText().equals("/start")){
            buildMessageService.buildButtons(t.getMessage());
        }
    }
}
