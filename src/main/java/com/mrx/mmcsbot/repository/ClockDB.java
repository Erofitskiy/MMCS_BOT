package com.mrx.mmcsbot.repository;

import com.mrx.mmcsbot.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClockDB {

    private Connection c = null;
    private Statement stmt = null;

    public ClockDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public boolean morningWasSent(){
        Calendar cal = Calendar.getInstance();

        Date now = new Date();
        System.out.println(now);
        System.out.println("year = " + cal.get(Calendar.YEAR) + " and " +
                "month = " + cal.get(Calendar.MONTH) + " and " +
                "day = " + cal.get(Calendar.DAY_OF_MONTH));


        System.out.println("cal = " + cal.get(Calendar.DAY_OF_MONTH));

        try {
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM log_morning_evening WHERE " +
                    "year = " + cal.get(Calendar.YEAR) + " and " +
                    "month = " + cal.get(Calendar.MONTH) + " and " +
                    "day = " + cal.get(Calendar.DAY_OF_MONTH));
            while (rs.next()){
                System.out.println("1");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        new ClockDB().morningWasSent();
    }


}
