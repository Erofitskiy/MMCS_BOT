package App;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.LinkedList;
import java.util.Random;

import static App.ScheduleManager.Today;
import static App.ScheduleManager.whatIsTheDay;


public class VKServer {
    public static VKCore vkCore;

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
        VKManager postman = new VKManager();
        LinkedList<String> phrases = new LinkedList<String>();
        phrases.add("Моя твоя не понимать((");
        phrases.add("Походу я баг нашел");
        phrases.add("$#3%#^%$4$%&*^4@!+");
        phrases.add("Схемы болят, давайте полегче");
        phrases.add("Схемы болят, давайте полегче");
        phrases.add("Отправлю это Создателю");
        phrases.add("Ого, а я только вот так умею: \n Пользователь удалён...");
        Random r = new Random();

        while (true) {
            Thread.sleep(300); // запрос каждые 0.3 сек
            try {
                Message message = vkCore.getMessage();
            if (message != null)
            {
                String text = message.getText();
                int userId = message.getFromId();
                System.out.println("Сообщение: " + text);
//                System.out.println("============");
//                System.out.println(message.getId());
//                System.out.println(message.getFromId());
//                System.out.println("============");
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
                    postman.sendMessage("Ready to wooork", userId);
                    readyToWrite = false;
                    db.CloseConnection();
                    continue;
                }

                switch (text) {
                    case "Возможности":
                        String help = "Список комманд: \n \n" +
                                "Начать - запоминание ботом вашей группы\n" +
                                "Расписание - вывод расписания на сегодня\n" +
                                "ЧужоеРасписание - узнать расписание другой группы\n" +
                                "Расписание пн - узнать своё расписание на понедельник\n";
                        postman.sendMessage(help, userId);
                        break;
                    case "начать":
                    case "Начать": {
                        String text1 = "";
                        SQLiteDB db = new SQLiteDB();
                        if (db.UserExists(userId))
                            text1 = "Давайте познакомимся снова \n";
                        text1 += "Введите курс и группу в формате: КУРС.ГРУППА \n" +
                                "Например: 3.1 ";
                        postman.sendMessage(text1, userId);
                        readyToWrite = true;
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
                        String text1 = "Введите курс и группу в формате: КУРС.ГРУППА \n" +
                                "Например: 3.1 ";
                        postman.sendMessage("Введите курс и группу в формате: КУРС.ГРУППА \n Например: 3.1 ", userId);
                        someoneElseSchedule = true;
                        break;
                    }
                    default:
                        postman.sendMessage(phrases.get(r.nextInt(phrases.size())), userId);
                        break;
                }
            }
            } catch (ClientException e) {
                System.out.println("Возникли проблемы");
                System.out.println(e.getMessage());
                e.printStackTrace();
                final int RECONNECT_TIME = 10000;
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
}