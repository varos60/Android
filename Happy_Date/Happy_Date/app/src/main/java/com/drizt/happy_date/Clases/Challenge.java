package com.drizt.happy_date.Clases;

import org.json.JSONException;
import org.json.JSONObject;

public class Challenge {
    int id;
    String name;
    int who;
    int to_whom;
    String image;

    public Challenge(int id, String name, int who, int to_whom, String image) {
        this.id = id;
        this.name = name;
        this.who = who;
        this.to_whom = to_whom;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public int getTo_whom() {
        return to_whom;
    }

    public void setTo_whom(int to_whom) {
        this.to_whom = to_whom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Challenge(JSONObject jsonObject){
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            who = jsonObject.getInt("who");
            to_whom = jsonObject.getInt("to_whom");
            image = jsonObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
