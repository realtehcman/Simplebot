package com.tehcman.sendmessage;

import com.tehcman.BotEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageSenderImpl implements MessageSender{
    private BotEntryPoint entryPoint;

    @Autowired
    public void setEntryPoint(BotEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }


    @Override
    public void messageSend(SendMessage sendMessage) {
        try {
            entryPoint.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
