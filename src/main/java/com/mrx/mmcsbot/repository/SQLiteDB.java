package com.mrx.mmcsbot.repository;
import com.mrx.mmcsbot.dto.VKUser;
import com.google.gson.Gson;
import com.mrx.mmcsbot.model.User;
import com.vk.api.sdk.objects.messages.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static com.mrx.mmcsbot.service.ScheduleManager.GetRequest;

public class SQLiteDB {
    private Connection c = null;
    private Statement stmt = null;

    public SQLiteDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:UsersDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void ListUsers(){
        try {
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vkusers");
            while (rs.next()){
                int id = rs.getInt("vkid");
                int course = rs.getInt("mcourse");
                int group = rs.getInt("mgroup");
                System.out.println(id + " " + course + " " + group);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void CreateUser(Message message){
        try {
            String text = message.getText();
            Properties prop = new Properties();
            String access_token = "";
            try {
                prop.load(new FileInputStream("src/main/resources/vkconfig.properties"));
                access_token = prop.getProperty("accessToken");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка при загрузке файла конфигурации");
            }

            String json = GetRequest("https://api.vk.com/method/users.get?user_ids=" + message.getFromId()
                    +"&fields=bdate&access_token=" + access_token +"&v=5.102");
            json = json.replace("{\"response\":[", "");
            json = json.replace("]}", "");
            System.out.println(json);

            Gson gson = new Gson();
            VKUser userDATA = gson.fromJson(json, VKUser.class);
            System.out.println(userDATA.first_name);
            System.out.println(userDATA.last_name);

            String request = "INSERT INTO vkusers (vkid, mcourse, mgroup, mfirst_name, mlast_name) VALUES ("
                    + message.getFromId() + ", " + text.charAt(0) + ", " + text.charAt(2) +
                    ", \"" +  userDATA.first_name + "\",\"" + userDATA.last_name + "\");";
            this.stmt = c.createStatement();
            stmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User GetUser(int id){
        int course = 0;
        int group = 0;
        try{
        String request = "SELECT mcourse, mgroup "
                + "FROM vkusers WHERE vkid =" + id;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(request);
            if (rs.next()){
                course = rs.getInt(1);
                group = rs.getInt(2);
            }
        } catch (SQLException e) {  e.printStackTrace(); }
        return new User(id, course, group);
    }

    public void UpdateUser(int id, int course, int group){
        try {
            String request = "UPDATE vkusers \n" +
                    "SET mcourse = " + course + ", mgroup = " + group + "\n" +
                    "WHERE vkid = " + id + ";";
            this.stmt = c.createStatement();
            stmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean UserExists(int id){
        boolean result = false;
        try {
            String request= "select * from vkusers where vkid=" + id ;
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(request);
            result = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void CloseConnection(){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
