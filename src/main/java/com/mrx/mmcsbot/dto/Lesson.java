package com.mrx.mmcsbot.dto;

public class Lesson {
    public int id;
    public int uberid;
    public int subcount;
    public String timeslot;


    public Lesson(int id, int uberid, int subcount, String timeslot){
        this.id = id;
        this.uberid = uberid;
        this.subcount = subcount;
        this.timeslot = timeslot;
    }

    @Override
    public String toString() {
        return "[ id: " + id + ", uberid: " + uberid +
                ", subcount: " + subcount + ", timeslot: " + timeslot + " ]";
    }

}

