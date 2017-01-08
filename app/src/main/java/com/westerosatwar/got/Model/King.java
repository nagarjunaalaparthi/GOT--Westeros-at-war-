package com.westerosatwar.got.Model;

import java.io.Serializable;

/**
 * Created by Arjun.
 */

public class King implements Serializable{

    private String name;
    private int high_rating = 0;
    private int rating = 400;
    private int battlesWon;
    private int battlesLost;
    private String strength;
    private String battleType;

    private String high_rating_to_show;
    private String strength_to_show;

    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHigh_rating() {
        return high_rating;
    }

    public void setHigh_rating(int high_rating) {
        this.high_rating = high_rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public void setBattlesWon(int battlesWon) {
        this.battlesWon = battlesWon;
    }

    public int getBattlesLost() {
        return battlesLost;
    }

    public void setBattlesLost(int battlesLost) {
        this.battlesLost = battlesLost;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getBattleType() {
        return battleType;
    }

    public void setBattleType(String battleType) {
        this.battleType = battleType;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHigh_rating_to_show() {
        return "Highest Rating : " + high_rating;
    }

    public void setHigh_rating_to_show(String high_rating_to_show) {
        this.high_rating_to_show = high_rating_to_show;
    }

    public String getStrength_to_show() {
        return "Battle Strength : " + strength;
    }

    public void setStrength_to_show(String strength_to_show) {
        this.strength_to_show = strength_to_show;
    }
}
