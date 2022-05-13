package com.tehcman;

import com.tehcman.services.BuildMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class BotEntryPoint extends TelegramLongPollingBot {
    private BuildMessageService buildMessageService;

    @Autowired //spring will initialize
    public void setBuildMessageService(BuildMessageService buildMessageService) {
        this.buildMessageService = buildMessageService;
    }

    @Value("${telegrambot.botToken}")
    private String botToken ;
    @Value("${telegrambot.botName}")
    private String botName;


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        log.info("New message from User:{}, chatId: {},  with text: {}",
                message.getFrom().getUserName(), message.getChatId(), message.getText());

        if (message.hasText()){
            buildMessageService.buildTGmessage(message);
        }
    }
}