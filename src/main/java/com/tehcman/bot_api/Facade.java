package com.tehcman.bot_api;

import com.tehcman.cache.BotState;
import com.tehcman.cache.UserData;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component //autowires
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facade {

    UserData userData;
    BotStateContext botStateContext;

    public SendMessage handleUpdate(Update update) {
        long chatId = update.getMessage().getChat().getId();
        String userText = update.getMessage().getText();

        if (update.hasMessage() && update.getMessage() != null) {
            log.info("User chat id {} and their message {}", chatId, userText);
            return handleUserMessage(update.getMessage());
        } else try {
            throw new Exception("Not valid user input");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SendMessage handleUserMessage(Message message) {
        int userId = message.getFrom().getId();
        String userText = message.getText();

        BotState botState;
        switch (userText) {
            case "/start":
                botState = BotState.FILLING_PROFILE;
                break;
            case "Help":
                botState = BotState.SHOW_HELP_MENU;
                break;
            case "Show":
                botState = BotState.PROFILE_FILLED;
                break;
            default:
                botState = BotState.FILLING_PROFILE;
                break;
        }

        SendMessage sendMessage;
        userData.setUsersCurrentBotState(userId, botState);

        sendMessage = botStateContext.processMessage(botState, message);
        return sendMessage;

    }


}
