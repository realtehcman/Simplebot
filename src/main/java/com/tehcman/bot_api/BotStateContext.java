package com.tehcman.bot_api;

import com.tehcman.cache.BotState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class BotStateContext {
    Map<BotState, InputMessageHandler> messageHandler = new HashMap();

//?
    public BotStateContext(List<InputMessageHandler> handlers) {
        handlers.forEach(handler -> messageHandler.put(handler.getHandlerName(), handler));
    }

    public SendMessage processMessage(BotState botState, Message message) {
        InputMessageHandler currentMessageHandler = messageHandler.get(botState);
        return currentMessageHandler.handle(message);
    }
}
