package com.mrx.mmcsbot.vkmessenger;

import com.mrx.mmcsbot.keyboard.KeyboardHolder;
import com.mrx.mmcsbot.model.User;
import com.mrx.mmcsbot.repository.SQLiteDB;
import com.mrx.mmcsbot.service.ScheduleManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mrx.mmcsbot.service.ScheduleManager.Today;
import static com.mrx.mmcsbot.service.ScheduleManager.whatIsTheDay;

public class MessageHandler {

    private final VKMessenger postman;
    private final List<String> phrases;

    public MessageHandler(){
        postman = new VKMessenger();

        phrases = new ArrayList<>();
        phrases.add("Моя твоя не понимать((");
        phrases.add("Походу я баг нашел");
        phrases.add("$#3%#^%$4$%&*^4@!+");
        phrases.add("Схемы болят, давайте полегче");
        phrases.add("Схемы болят, давайте полегче");
        phrases.add("Отправлю это Создателю");
        phrases.add("Ого, а я только вот так умею: \n Пользователь удалён...");

    }


    public void handle(Message message){
        Random r = new Random();
        String text = message.getText();
        int userId = message.getFromId();


        if (text.contains(".")){
            SQLiteDB db = new SQLiteDB();
            if(db.UserExists(userId)){
                User user = new User(userId, text);
                // перетащить парсинг курс+группа сюда, а не в юзера

                db.UpdateUser(userId, user.course, user.group);
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
