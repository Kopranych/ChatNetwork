package com.company.model.test;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {
    private String login;
    private String message;
    private String[] users;
    private Date time;
//конструктор для клиента
    public Message(String login, String massege) {
        this.login = login;
        this.message = massege;
        this.time = Calendar.getInstance().getTime();
    }
//конструктор для сервера
    public Message(String login, String massege, String[] users) {
        this.login = login;
        this.message = massege;
        this.users = users;
        this.time = Calendar.getInstance().getTime();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getOnlineUsers() {
        return users;
    }


    public String getTime() {
        Time t = new Time(this.time.getTime());
        return t.toString();
    }

}
