package com.tehcman.processors;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Processor {
    void handleStart(Update update);
    void handlePoem(Update update);
    void handleCallBackQuery(Update update);

    default void direct(Update update) {
        if (update.getMessage().getText().equals("/start")){
            handleStart(update);
        }
        else if (update.getMessage().hasText()){
            handlePoem(update); //implement handler for the start
        }
/*        else if (update.hasMessage() && !update.getMessage().equals("/start")){
            handleText(update);
        }*/
        else if (update.hasCallbackQuery()){
            handleCallBackQuery(update);
        }
    }

}
