package App;

import JSONClasses.curricula;
import JSONClasses.Group;
import JSONClasses.lesson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ScheduleManager {
    //private static String week = "lower";

    static int Today(){
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        String day = date.toString().substring(0,3);
        switch (day) {
            case  ("Mon"): return 0;
            case  ("Tue"): return 1;
            case  ("Wed"): return 2;
            case  ("Thu"): return 3;
            case  ("Fri"): return 4;
            case  ("Sat"): return 5;
            case  ("Sun"): return 6;
        }
        return 0;
    }


    public static String GetRequest(String urlAddress){
        String jsonString = "";
        try{
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) { jsonString += output; }
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace(); }
        return jsonString;
    }

    private static int whatIsMagicNumber(User user){
        String json = GetRequest("http://schedule.sfedu.ru/APIv1/group/forGrade/" + user.course);
        Gson gson = new Gson();
        Group[] groups = gson.fromJson(json, Group[].class);
        for(Group group : groups)
            if (group.num == user.group)
                return group.id;
        return 0;
    }

    public static void getSchedule(User user, int today, String week){
        VKManager postman = new VKManager();
        if(user.course == 0 || user.group == 0){
            postman.sendMessage("Я Вас не знаю((( Нажмите \"Начать\"", user.id);
            return;
        }
        String jsonString = GetRequest("http://schedule.sfedu.ru/APIv1/schedule/group/" + whatIsMagicNumber(user));
        if(jsonString.equals("")){
            postman.sendMessage("Произошла ошибка", user.id);
            return;
        }

        String[] JJson = jsonString.split(",\"c");
        String json1 = JJson[0];
        String json2 = JJson[1];

        json1 = json1.replaceFirst("\\{\"lessons\":\\[", "\\[");
        json2 = json2.replaceFirst("urricula\":\\[", "\\[");
        json2 = json2.substring(0, json2.length() - 1);

        Gson gson = new Gson();
        lesson[] lessonsDate = gson.fromJson(json1, lesson[].class);
        curricula[] lessonsName = gson.fromJson(json2, curricula[].class);

        String lessonsToday = "Расписание на  " + whatIsTheDay(today) + ": \n";


        LinkedList<String> list = new LinkedList<String>();

        for(lesson date : lessonsDate) {
            for(curricula name : lessonsName) {
                if (Character.getNumericValue((date.timeslot.charAt(1))) == today && name.lessonid == date.id) {
                    String typeOfWeek = date.timeslot.substring(21, 26);
                    if(typeOfWeek.equals("full)") || typeOfWeek.equals(week)){
                        String time = date.timeslot.substring(3,17)
                            .replaceFirst(":00", "")
                            .replace(",", "-");
                        list.add(time + " ауд. " + name.roomname + " " + name.subjectabbr +   "\n");
                    }
                }
            }
        }
        java.util.Collections.sort(list);
        for(String str : list)
            lessonsToday += str;
        if(list.isEmpty()){
            postman.sendMessage(lessonsToday + "У Вас нет пар по расписанию ", user.id);
        }
        else
            postman.sendMessage(lessonsToday, user.id);
    }

    private static String whatIsTheDay(int day){
        String answer = "";
        switch (day) {
            case 0:
                answer = "понедельник"; break;
            case 1:
                answer = "вторник"; break;
            case 2:
                answer = "среду"; break;
            case 3:
                answer = "четверг"; break;
            case 4:
                answer = "пятницу"; break;
            case 5:
                answer = "субботу"; break;
            case 6:
                answer = "воскресенье"; break;
        }
        return answer;
    }

    static int whatIsTheDay(String str){
        switch (str) {
            case "пн":
                return 0;
            case "вт":
                return 1;
            case "ср":
                return 2;
            case "чт":
                return 3;
            case "пт":
                return 4;
            case "сб":
                return 5;
            case "вс":
                return 6;
        }
        return 1;
    }
}
