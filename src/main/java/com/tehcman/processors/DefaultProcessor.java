package com.tehcman.processors;

import com.tehcman.handlers.CallBackQueryHandler;
import com.tehcman.handlers.TextHandler;
import com.tehcman.handlers.StartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcessor implements Processor{
    private final CallBackQueryHandler callBackQueryHandler;
    private final TextHandler textHandler;
    private final StartHandler startHandler;

    @Autowired
    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, TextHandler textHandler, StartHandler startHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.textHandler = textHandler;
        this.startHandler = startHandler;
    }


    @Override
    public void handleCallBackQuery(Update update) {
        callBackQueryHandler.handle(update);
    }


    @Override
    public void handleStart(Update update) {
        startHandler.handle(update);
    }

    @Override
    public void handlePoem(Update update) {
        textHandler.handle(update);
    }

}
