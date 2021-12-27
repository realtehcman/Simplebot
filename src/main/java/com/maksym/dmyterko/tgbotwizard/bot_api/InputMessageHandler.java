package com.maksym.dmyterko.tgbotwizard.bot_api;

import com.maksym.dmyterko.tgbotwizard.cache.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**Обработчик сообщений
 */
public interface InputMessageHandler {
    SendMessage handle(Message message);

    BotState getHandlerName();
}
