package com.tehcman.services;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Service
public class BuildSendMessageService implements IBuildSendMessageService {

    public SendMessage createHTMLMessage(String chatID, String text, ReplyKeyboardMarkup mainMarkup) {
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(chatID);
        sendThisMessage.setReplyMarkup(mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(text);
        return sendThisMessage;
    }
}