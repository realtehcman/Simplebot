package com.tehcman.services;

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class BuildButtonsService {
    private final MessageSender messageSender;
    private final BuildMessageService buildMessageService;
    private ArrayList<KeyboardRow> arrayOfKeyboardRows;
    private ReplyKeyboardMarkup mainMarkup;


    @Autowired
    public BuildButtonsService(MessageSender messageSender, BuildMessageService buildMessageService) {
        this.messageSender = messageSender;
        this.buildMessageService = buildMessageService;
        this.arrayOfKeyboardRows = new ArrayList<>();
        this.mainMarkup = new ReplyKeyboardMarkup();
    }

    public void buildButtons(Message message) {
        if (arrayOfKeyboardRows.size() > 0) {
            this.arrayOfKeyboardRows = new ArrayList<>();
            this.mainMarkup = new ReplyKeyboardMarkup();
        }


        String messageToTheUser = buildMessageService.chooseMsgForUser(message);

        var row1 = new KeyboardRow();
        row1.add("I want a joke"); //check for the poem
        row1.add("You're dumb");

        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("Temporary save my info into the cache");
        row2.add(button3);


        Collections.addAll(arrayOfKeyboardRows, row1, row2);

        mainMarkup.setKeyboard(arrayOfKeyboardRows);
        mainMarkup.setResizeKeyboard(true);

        messageSender.messageSend(newHTMLSendMessage(message.getChatId().toString(), messageToTheUser));

    }

    public void afterRegistrationButtons(Message message) {
        /*if (arrayOfKeyboardRows.size()>0){
            this.arrayOfKeyboardRows = new ArrayList<>();
            this.mainMarkup = new ReplyKeyboardMarkup();
        }*/

        //TODO should i keep it
        String messageToTheUser = buildMessageService.chooseMsgForUser(message);

        arrayOfKeyboardRows.remove(1);
        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row2.add(button3);
        row2.add(button4);


        Collections.addAll(arrayOfKeyboardRows, row2);

/*        mainMarkup.setKeyboard(arrayOfKeyboardRows);
        mainMarkup.setResizeKeyboard(true);*/



        messageSender.messageSend(newHTMLSendMessage(message.getChatId().toString(), messageToTheUser));

    }

    //triggers if we register a new user

    public void addingPhoneNumberButton(Message message) {
        var markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);


        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSahringPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();

        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSahringPhoneNumber);

        var arrayOfKeyboardRows = new ArrayList<KeyboardRow>();
        arrayOfKeyboardRows.add(row1);


        markup.setKeyboard(arrayOfKeyboardRows);
        markup.setResizeKeyboard(Boolean.TRUE);

        //without the following lines, buttons won't build


        messageSender.messageSend(newHTMLSendMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button"));
    }

    public SendMessage newHTMLSendMessage(String chatID, String text) {
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(chatID);
        sendThisMessage.setReplyMarkup(this.mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(text);
        return sendThisMessage;
    }


}
