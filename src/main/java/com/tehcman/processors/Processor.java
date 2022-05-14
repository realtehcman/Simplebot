package com.tehcman.processors;

/*!!!!!!!!!
 * Possible error Update.getMessage()" is null callbackquery
 * if I dont check if update has the message!
 * */

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Position;
import com.tehcman.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor {

    abstract void handleText(Update update);

    abstract void handleCallBackQuery(CallbackQuery update);

    abstract void handleSaveToCache(Message message);

    private Cache<User> userCache;

    @Autowired
    public void setAge(Cache<User> userCache) {
        this.userCache = userCache;
    }

    public void direct(Update update) {
        //active registration
        User userFromCache = userCache.findBy(update.getMessage().getChatId());
        if ((userFromCache != null) && !userFromCache.getPosition().equals(Position.NONE)) {
            switch (userFromCache.getPosition()) {
                case PHONE_NUMBER:
                case AGE:
                    handleSaveToCache(update.getMessage());
            }
        } else if (update.getMessage().getText().equals("Temporary save my info into the cache")) {
            handleSaveToCache(update.getMessage());
        } else {
            if (update.getMessage().getText() != null) {
                handleText(update);
            } else if (update.hasCallbackQuery()) {
                handleCallBackQuery(update.getCallbackQuery());
            } /*else if ((update.hasMessage() && update.getMessage().getText().equals("Temporary save my info into the cache")) ) {
                handleSaveToCache(update.getMessage());
            } */
        }
    }
}
