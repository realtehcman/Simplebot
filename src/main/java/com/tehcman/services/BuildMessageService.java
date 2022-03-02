package com.tehcman.services;
// TODO: 2/18/2022

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class BuildMessageService {

    @Autowired
    private MessageSender messageSender;

    //spring init it with component/bean that implement MessageSender interface
    @Autowired
    public BuildMessageService(@Lazy MessageSender messageSender) {
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

    public void buildButtons(Message message) {
        if (!message.getText().equals("/start")){
            throw new InputMismatchException("Invalid input");
        }

        var markup = new ReplyKeyboardMarkup();
        var arrayOfKeyboardRows = new ArrayList<KeyboardRow>();

//        var button1 = new KeyboardButton("You're dumb1");
//        var button2 = new KeyboardButton("You're dumb2");

        var row1 = new KeyboardRow();
        row1.add("give me the text"); //check for the poem
        row1.add("You're dumb 2");

        var button3 = KeyboardButton.builder().text("phone number").requestContact(Boolean.TRUE).build();
        var button4 = new KeyboardButton("Where's ur ass?");
        button4.setText("Share location"); //overwrites the prev name
        button4.setRequestLocation(Boolean.TRUE);

        var row2 = new KeyboardRow();
        row2.add(button3);
        row2.add(button4);

        var button5 = new KeyboardButton("Poll");

        var poll = new KeyboardButtonPollType();
        button5.setRequestPoll(poll);


        var row3 = new KeyboardRow();
        row3.add(button5);

        Collections.addAll(arrayOfKeyboardRows, row1, row2, row3);

        markup.setKeyboard(arrayOfKeyboardRows);
        markup.setResizeKeyboard(Boolean.TRUE);
        markup.getOneTimeKeyboard();

        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(message.getChatId().toString());
        sendThisMessage.setReplyMarkup(markup);
        sendThisMessage.setText("coci");

        messageSender.messageSend(sendThisMessage);

    }

}
/*

//        button5.setRequestPoll(pollNudes);

        List<String> options = new ArrayList<>();
        options.add("Yes!");
        options.add("love'em :3");
        options.add("send a dick pick");

// Let's just assume we get the chatMessage as a parameter. For example from the message received, or from a database
        SendPoll ourPoll = new SendPoll(message.getChatId().toString(), "Do you like nudes",
                options);

        messageSender.messageSend();
*/
