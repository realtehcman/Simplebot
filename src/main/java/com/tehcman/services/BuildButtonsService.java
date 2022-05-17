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
    private final ArrayList<KeyboardRow> arrayOfKeyboardRows;
    private final ReplyKeyboardMarkup mainMarkup;


    @Autowired
    public BuildButtonsService(MessageSender messageSender, BuildMessageService buildMessageService) {
        this.messageSender = messageSender;
        this.buildMessageService = buildMessageService;
        this.arrayOfKeyboardRows = new ArrayList<>();
        this.mainMarkup = new ReplyKeyboardMarkup();

        //prettifies the buttons
        this.mainMarkup.setKeyboard(arrayOfKeyboardRows);
        this.mainMarkup.setResizeKeyboard(true);
    }

    public void beforeRegistrationButtons(Message message) {
        arrayOfKeyboardRows.clear();

        String messageToTheUser = buildMessageService.chooseMsgForUser(message);

        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("Temporary save my info into the cache");
        row2.add(button3);

        Collections.addAll(arrayOfKeyboardRows, createCommonButtonsRow(), row2);

        messageSender.messageSend(createHTMLMessage(message.getChatId().toString(), messageToTheUser));
    }


    //triggers if we register a new user
    public void addingPhoneNumberButton(Message message) {
        arrayOfKeyboardRows.clear();

        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSharingPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSharingPhoneNumber);

        arrayOfKeyboardRows.add(row1);

        messageSender.messageSend(createHTMLMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button"));
    }

    public void afterRegistrationButtons(Message message) {
        arrayOfKeyboardRows.clear();

        //TODO should i keep it
        String messageToTheUser = buildMessageService.chooseMsgForUser(message);


        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row2.add(button3);
        row2.add(button4);

        Collections.addAll(arrayOfKeyboardRows, createCommonButtonsRow(), row2);


        messageSender.messageSend(createHTMLMessage(message.getChatId().toString(), messageToTheUser));
    }

    private SendMessage createHTMLMessage(String chatID, String text) {
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(chatID);
        sendThisMessage.setReplyMarkup(this.mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(text);
        return sendThisMessage;
    }

    //this buttons used for before and after registration
    private KeyboardRow createCommonButtonsRow() {
        var row1 = new KeyboardRow();
        row1.add("I want a joke");
        row1.add("You're dumb");
        return row1;
    }

}
