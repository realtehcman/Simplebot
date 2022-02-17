package com.tehcman.services;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class BuildMessageService {
    private MessageSender messageSender;

    //spring init it with component/bean that implement MessageSender interface
    @Autowired
    public BuildMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    public void buildTGmessage(Message message) {
        var ms1 = SendMessage.builder() //static method; otherwise i need implement it by myself
                .text("<u>Testing this shit</u>")
                .chatId(message.getChatId().toString())
                .parseMode("HTML")
                .build();

        messageSender.messageSend(ms1);
    }

}
