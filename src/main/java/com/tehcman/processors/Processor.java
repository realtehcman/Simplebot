package com.tehcman.processors;

import com.tehcman.handlers.CallBackQueryHandler;
import com.tehcman.handlers.TextHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Processor {
    void handleCallBackQuery(Update update);
//    void handleText(Update update);
    void handleStart(Update update);

    default void direct(Update update) {
        if (update.hasMessage()){
            handleStart(update); //implement handler for the start
        }
/*        else if (update.hasMessage() && !update.getMessage().equals("/start")){
            handleText(update);
        }*/
        else if (update.hasCallbackQuery()){
            handleCallBackQuery(update);
        }
    }

}
