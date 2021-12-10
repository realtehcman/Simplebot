package com.maksym.dmyterko.tgbotwizard.models;

/*Class description for the Bean wrapper (BotConfig)*/

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Setter
//Java Abstract class can implement interfaces without even providing the implementation of interface methods.
public class WizardBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;


    public WizardBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    //    update is the way to get everything from the client (tg id, chat id, message...)

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();


            try {
                execute(new SendMessage(chat_id, "Hi " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null;
    }






}
