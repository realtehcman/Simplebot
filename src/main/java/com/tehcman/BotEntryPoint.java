package com.tehcman;

import com.tehcman.processors.Processor;
import com.tehcman.services.BuildMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class BotEntryPoint extends TelegramLongPollingBot {
    private BuildMessageService buildMessageService;
    private Processor processor;

    @Value("${telegrambot.botToken}")
    private String botToken ;
    @Value("${telegrambot.botName}")
    private String botName;

    @Autowired
    public BotEntryPoint(BuildMessageService buildMessageService, Processor processor) {
        this.buildMessageService = buildMessageService;
        this.processor = processor;
    }


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
        log.info("New message from User:{}, chatId: {},  with text: {}",
                update.getMessage().getFrom().getUserName(),update.getMessage().getChatId(),
                update.getMessage().getText());

        processor.direct(update);
    }

}