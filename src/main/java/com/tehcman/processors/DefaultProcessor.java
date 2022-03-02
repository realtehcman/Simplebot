package com.tehcman.processors;

import com.tehcman.handlers.CallBackQueryHandler;
import com.tehcman.handlers.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcessor implements Processor{
    private final CallBackQueryHandler callBackQueryHandler;
    private final TextHandler textHandler;

    @Autowired
    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, TextHandler textHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.textHandler = textHandler;
    }


    @Override
    public void handleCallBackQuery(Update update) {
        callBackQueryHandler.iterateThroughData(update);
    }



    @Override
    public void handleStart(Update update) {
        textHandler.iterateThroughData(update);
    }

}
