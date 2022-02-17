package com.tehcman;

import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotEntryPoint extends TelegramWebhookBot {
    private BuildMessageService buildMessageService;

    @Value("${telegrambot.botToken}")
    private String botToken;

    @Value("${telegrambot.botName}")
    private String botName;

    @Value("%{telegrambot.webHookPath}")
    private String webHookPath;

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public String getBotPath() {
        return this.webHookPath;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
//            SendMessage messageForUser = buildMessageService.buildTGmessage(update.getMessage());
            SendMessage messageForUser = new SendMessage();
            messageForUser.setChatId(update.getMessage().getChatId().toString());
            messageForUser.setText("Fuk u");
            return messageForUser;
        }
        return null;
    }

    @Autowired
    public void setBuildMessageService(BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }
}
