package com.mrx.mmcsbot;

import com.mrx.mmcsbot.keyboard.KeyboardHolder;
import com.mrx.mmcsbot.model.User;
import com.mrx.mmcsbot.repository.SQLiteDB;
import com.mrx.mmcsbot.service.ScheduleManager;
import com.mrx.mmcsbot.vkmessenger.MessageHandler;
import com.mrx.mmcsbot.vkmessenger.VKMessenger;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mrx.mmcsbot.service.ScheduleManager.Today;
import static com.mrx.mmcsbot.service.ScheduleManager.whatIsTheDay;


public class VKServer {
    public static VKCore vkCore;
    final static int RECONNECT_TIME = 10000;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NullPointerException, ApiException, InterruptedException {
        System.out.println("Running server...");
        boolean readyToWrite = false;
        boolean someoneElseSchedule = false;
        VKMessenger postman = new VKMessenger();
        MessageHandler messageHandler = new MessageHandler();

        while (true) {
            Thread.sleep(300); // запрос каждые 0.3 сек
            try {
                Message message = vkCore.getMessage();
            if (message != null)
            {
                String text = message.getText();
                int userId = message.getFromId();
                System.out.println("Сообщение: " + text);

                if(someoneElseSchedule){
                    User user = new User(userId, text);
                    ScheduleManager.getSchedule(user, Today(), "lower");
                    someoneElseSchedule = false;
                    continue;
                }
                if(readyToWrite){
                    SQLiteDB db = new SQLiteDB();
                    if(db.UserExists(userId)){
                        User user = new User(userId, text);
                        db.UpdateUser(userId, user.course, user.group);
                    } else {
                        db.CreateUser(message);
                    }
                    postman.sendMessage("Ready to wooork", userId, KeyboardHolder.getWorkKeyboard());
                    readyToWrite = false;
                    db.CloseConnection();
                    continue;
                }

                messageHandler.handle(message);
            }
            } catch (ClientException e) {
                System.out.println("Возникли проблемы");
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                System.out.println(e.getMessage());
                e.printStackTrace();
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
}