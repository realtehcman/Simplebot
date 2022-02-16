package com.tehcman.bot_api.handlers;

import com.tehcman.bot_api.InputMessageHandler;
import com.tehcman.cache.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class FillingProfile implements InputMessageHandler {

    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return null;
    }
}
