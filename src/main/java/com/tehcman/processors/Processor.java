package com.tehcman.processors;

/*!!!!!!!!!
 * Possible error Update.getMessage()" is null callbackquery
 * if I dont check if update has the message!
 * */

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Processor {
    void handleStart(Update update);

    void handleText(Update update);

    void handleCallBackQuery(CallbackQuery update);

    void handleSaveToCache(Message message);


    default void direct(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            handleStart(update);
        } else if (update.hasCallbackQuery()) {
            handleCallBackQuery(update.getCallbackQuery());
        } else if (update.hasMessage() && update.getMessage().getText().equals("Temporary save my info into the cache")) {
            handleSaveToCache(update.getMessage());
        } else {
            handleText(update);
        }
    }

}
