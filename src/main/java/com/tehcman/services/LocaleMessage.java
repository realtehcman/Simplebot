package com.tehcman.services;
/*Object holds the locale and user message info
* */
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleMessage {
    private final MessageSource messageSource;
    private final Locale locale;

    //subject to change for dynamical locale assignment
    public LocaleMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.ENGLISH;
    }

    public String getMessageSource(String message){
        return messageSource.getMessage(message, null, locale);
    }
    public String getMessageSource(String message, Object... args) {
        return messageSource.getMessage(messageSource.toString(), args, locale);
    }
}
