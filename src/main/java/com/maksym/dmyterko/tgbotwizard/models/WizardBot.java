package com.maksym.dmyterko.tgbotwizard.models;

/*Class description for the Bean wrapper (BotConfig)*/

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WizardBot extends TelegramWebhookBot {
    private String getBotUsername;
    private String getBotToken;
    private String getBotPath;


//    update is the way to get everything from the client (tg id, chat id, message...)
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}
