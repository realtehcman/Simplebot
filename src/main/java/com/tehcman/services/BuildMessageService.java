package com.tehcman.services;
// TODO: 2/18/2022

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class BuildMessageService {

    private final ReplyKeyboardMarkup markup;
    private final ArrayList<KeyboardRow> arrayOfKeyboardRows;

    private final KeyboardRow row1;
    private final KeyboardRow row2;


    private final MessageSender messageSender;

    //spring init it with component/bean that implement MessageSender interface
    @Autowired
    public BuildMessageService(@Lazy MessageSender messageSender) {
        this.messageSender = messageSender;
        this.row1 = new KeyboardRow();
        this.row2 = new KeyboardRow();

        this.markup = new ReplyKeyboardMarkup();
        this.arrayOfKeyboardRows = new ArrayList<>();
    }


    public void buildTGmessageTest(Message message) {
        var ms1 = SendMessage.builder() //static method; otherwise i need implement it by myself
                .text("<u>Testing this shit</u>")
                .chatId(message.getChatId().toString())
                .parseMode("HTML")
                .build();

        messageSender.messageSend(ms1);
    }

    public void buildButtons(Message message) {
        String messageToTheUser = chooseMsgForUser(message);


        row1.add("I want a joke"); //check for the poem
        row1.add("You're dumb");

        var button3 = new KeyboardButton("Temporary save my info into the cache");

        row2.add(button3);


        Collections.addAll(arrayOfKeyboardRows, row1, row2);

        markup.setKeyboard(arrayOfKeyboardRows);
        markup.setResizeKeyboard(Boolean.TRUE);
        markup.getOneTimeKeyboard();

        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(message.getChatId().toString());
        sendThisMessage.setReplyMarkup(markup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(messageToTheUser);

        messageSender.messageSend(sendThisMessage);
    }

    //after pressing a button the user will receive a message
    private String chooseMsgForUser(Message message) {
        String messageToTheUser;
        switch (message.getText()) {
            case "/start":
                messageToTheUser = "Yay! You've just launched this bot!";
                break;
            case "Temporary save my info into the cache":
                messageToTheUser = "Press button Phone Number";
                break;
            default:
                messageToTheUser = "ok";
        }
        return messageToTheUser;
    }

    //triggers if we register a new user
    public void addingPhoneNumberButton(){
        var button4 = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        row2.add(button4);
        Collections.addAll(arrayOfKeyboardRows, row1, row2);
    }

    public void removingPhoneNumberButton(){
        row2.remove("Phone number");
    }

    public void removingNewUserRegistrationButton(){
        row2.remove("Temporary save my info into the cache");
    }

}
