package com.maksym.dmyterko.tgbotwizard.services;

/*Class that prepares the ready messages (SendMessage)_
 * */

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ReplyMessage {
    private LocaleMessage localeMessage;

    public ReplyMessage(LocaleMessage localeMessage) {
        this.localeMessage = localeMessage;
    }

    public SendMessage getMessage(String text, int chatId) {

// ? correct
        SendMessage sendMessage = new SendMessage(Long.valueOf(chatId), localeMessage.getMessageSource(text));
        return sendMessage;
    }

// ? is it really needed
    public SendMessage getMessage(String text, int chatId, Object... args) {
        SendMessage sendMessage = new SendMessage(Long.valueOf(chatId),
                localeMessage.getMessageSource(text, args));
        return sendMessage;
    }


}
