package com.tehcman.services;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class BuildMessageService {
    private final MessageSender messageSender;

    public BuildMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    public SendMessage buildTGmessage(Message message){
        var ms1 = SendMessage.builder() //static method; otherwise i need implement it by myself
                .text("Testing this shit")
                .chatId(message.getChatId().toString())
                .parseMode("HTML")
                .build();
        return ms1;
    }
}
/*var ms1 = SendMessage.builder()
                .text("<b>Bold</b> " +
                        "<i>italic</i>" +
                        " <code>mono</code> " +
                        "<a href=\"google.com\">Google</a>")
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .build();

        messageSender.sendMessage(ms1);*/