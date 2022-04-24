package com.mrx.mmcsbot.model;

import com.mrx.mmcsbot.keyboard.KeyboardHolder;
import com.mrx.mmcsbot.vkmessenger.VKMessenger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class User {
    private int course;
    private int group;
    private int id;
    private int morning;
    private int evening;

    public User(int id, int course, int group){
        this.course = course;
        this.group = group;
        this.id = id;
    }

    public User(int course, int group, int id, int morning, int evening) {
        this.course = course;
        this.group = group;
        this.id = id;
        this.morning = morning;
        this.evening = evening;
    }

    public User(int id, String data){
        this.id = id;

        try{
            int d0 = Integer.parseInt(String.valueOf(data.charAt(0)));
            int d2 = Integer.parseInt(String.valueOf(data.charAt(2)));

            if(d0 == 0 || d2 == 0)
                throw new NumberFormatException();

            if(data.length() == 3){
                this.course = d0;
                this.group = d2;
            }
            else {
                this.course = d0;
                this.group = d2*10 + Integer.parseInt(String.valueOf(data.charAt(3)));
            }
        }catch(NumberFormatException e){
            VKMessenger postman = new VKMessenger();
            postman.sendMessage("Некорректные данные. Начните сначала.", id, KeyboardHolder.getWorkKeyboard());
        }

    }
}
