package com.drizt.happy_date.Clases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categorie {
    int id;
    String name;
    ArrayList<Challenge> challenges;

    public Categorie(int id, String name, ArrayList<Challenge> challenges) {
        this.id = id;
        this.name = name;
        this.challenges = challenges;
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

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(ArrayList<Challenge> challenges) {
        this.challenges = challenges;
    }

    public Categorie(JSONObject jsonObject){
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            JSONArray jsonChallenges = jsonObject.optJSONArray("challenges");
            ArrayList<Challenge> list_challenges = new ArrayList<>();
            for (int i=0; i < jsonChallenges.length();i++){
                list_challenges.add(new Challenge(jsonChallenges.getJSONObject(i)));
            }
            challenges = list_challenges;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
