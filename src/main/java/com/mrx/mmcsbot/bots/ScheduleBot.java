package com.mrx.mmcsbot.bots;

import com.mrx.mmcsbot.Application;
import com.mrx.mmcsbot.VKCore;
import com.mrx.mmcsbot.keyboard.KeyboardHolder;
import com.mrx.mmcsbot.model.User;
import com.mrx.mmcsbot.repository.SQLiteDB;
import com.mrx.mmcsbot.service.ScheduleManager;
import com.mrx.mmcsbot.vkmessenger.MessageHandler;
import com.mrx.mmcsbot.vkmessenger.VKMessenger;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.mrx.mmcsbot.service.ScheduleManager.*;

public class ScheduleBot implements Runnable {

    final static int RECONNECT_TIME = 10000;
    private final VKMessenger postman;
    private final List<String> phrases;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleBot.class);


    public ScheduleBot(){
        postman = new VKMessenger();

        phrases = new ArrayList<>();
        phrases.add("Моя твоя не понимать((");
        phrases.add("Походу я баг нашел");
        phrases.add("$#3%#^%$4$%&*^4@!+");
        phrases.add("Схемы болят, давай полегче");
        phrases.add("Схемы болят, давай полегче");
        phrases.add("Отправлю это Создателю");
        phrases.add("Ого, а я только вот так умею: \n Пользователь удалён...");

    }


    @Override
    public void run() {
        try {
            VKCore vkCore = new VKCore();

            while (true) {
                Thread.sleep(300); // запрос каждые 0.3 сек
                try {
                    Message message = vkCore.getMessage();
                    if (message != null) {
                        logger.info("[" + message.getFromId() + "] От пользователя <<" + message.getText() + ">>");
                        handle(message);
                    }

                    Date now = new Date();
                    if(now.getHours() == 2){
                        sendMorning();
                    } else if (now.getHours() == 3){
                        sendEvening();
                    }

                } catch (ClientException | ApiException e) {
                    System.out.println("Возникли проблемы");
                    System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    Thread.sleep(RECONNECT_TIME);
                    vkCore = new VKCore();
                }
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    private void sendMorning(){
        SQLiteDB db = new SQLiteDB();

        System.out.println(db.getIdsMorning());

        db.getIdsMorning().stream()
                .filter(user -> user.getMorning() > 0)
                .forEach(user ->postman.sendMessage(
                                getSchedule1(user, Today(), "lower"),
                                user.getId(),
                                KeyboardHolder.getWorkKeyboard()
                        )
                );
    }

    private void sendEvening(){
        SQLiteDB db = new SQLiteDB();

        System.out.println(db.getIdsMorning());

        db.getIdsMorning().stream()
                .filter(user -> user.getMorning() > 0)
                .forEach(user ->postman.sendMessage(
                                getSchedule1(user, (Today() + 1) % 7, "lower"),
                                user.getId(),
                                KeyboardHolder.getWorkKeyboard()
                        )
                );
    }




    private void handle(Message message){
        Random r = new Random();
        String text = message.getText();
        int userId = message.getFromId();

        if (text.contains(".")){
            SQLiteDB db = new SQLiteDB();
            if(db.UserExists(userId)){
                User user = new User(userId, text);
                // перетащить парсинг курс+группа сюда, а не в юзера

                db.UpdateUser(userId, user.getCourse(), user.getGroup());
            } else {
                db.CreateUser(message);
            }
            postman.sendMessage("Ready to wooork", userId, KeyboardHolder.getWorkKeyboard()); // Новая клавиатура
            db.CloseConnection();
            return;
        }


        switch (text) {
            case "Возможности":
                String help = "Список комманд: \n \n" +
                        "Начать - запоминание ботом вашей группы\n" +
                        "Расписание - вывод расписания на сегодня\n" +
                        "ЧужоеРасписание - узнать расписание другой группы\n" +
                        "Расписание пн - узнать своё расписание на понедельник\n";
                postman.sendMessage(help, userId, KeyboardHolder.getWorkKeyboard());
                break;
            case "начать":
            case "Начать": {
                String text1 = "";
                SQLiteDB db = new SQLiteDB();
                if (db.UserExists(userId))
                    text1 = "Давайте познакомимся снова \n";
                text1 += "Введите курс и группу в формате: КУРС.ГРУППА \n" +
                        "Например: 3.1 ";
                postman.sendMessage(text1, userId, KeyboardHolder.getWelcomeKeyboard());
                //readyToWrite = true;
                db.CloseConnection();
                break;
            }
            case "расписание":
            case "Расписание": {
                SQLiteDB db = new SQLiteDB();
                User user = db.GetUser(userId);
                ScheduleManager.getSchedule(user, Today(), "lower");
                db.CloseConnection();
                break;
            }
            case "Расписание пн":
            case "Расписание вт":
            case "Расписание ср":
            case "Расписание чт":
            case "Расписание пт":
            case "Расписание сб":
            case "Расписание вс":{
                SQLiteDB db = new SQLiteDB();
                User user = db.GetUser(userId);
                db.CloseConnection();
                ScheduleManager.getSchedule(user, whatIsTheDay(text.substring(11,13)), "upper");
                break;
            }
            case "ЧужоеРасписание":
            case "чужоеРасписание": {
                postman.sendMessage("Введите курс и группу в формате: КУРС.ГРУППА \n Например: 3.1 ", userId, KeyboardHolder.getWorkKeyboard());
                //someoneElseSchedule = true;
                break;
            }
            default:
                postman.sendMessage(phrases.get(r.nextInt(phrases.size())), userId, KeyboardHolder.getWorkKeyboard());
                break;
        }
    }


}
