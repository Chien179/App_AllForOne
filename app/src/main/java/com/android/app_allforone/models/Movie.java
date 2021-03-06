package com.android.app_allforone.models;

import java.util.ArrayList;

public class Movie {
    private String coverImage;
    private String desc;
    private int duration;
    private String image;
    private String link;
    private String name;
    private String nation;
    private String release;
    private ArrayList<String> category;

    public Movie()
    {

    }

    public Movie(String name, String release, String desc) {
        this.name = name;
        this.release = release;
        this.desc = desc;
    }

    public Movie(String name, String release, String desc, int duration, String nation, String image, String coverImage, String link, ArrayList<String> category){
        this.name = name;
        this.desc = desc;
        this.release = release;
        this.duration = duration;
        this.nation = nation;
        this.image = image;
        this.coverImage = coverImage;
        this.link = link;
        this.category = category;
    }

    public Movie(String name)
    {
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRelease() {
        return release;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public ArrayList<String> getCategory() {
        return category;
    }
}
