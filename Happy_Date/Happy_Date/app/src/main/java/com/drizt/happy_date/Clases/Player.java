package com.drizt.happy_date.Clases;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    int id;
    String name;
    int gender;

    public Player(int id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Player(JSONObject jsonObject){
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            gender = jsonObject.getInt("gender");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
