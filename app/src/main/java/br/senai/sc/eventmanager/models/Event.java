package br.senai.sc.eventmanager.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Event implements Serializable {

    private int id;
    private String name;
    private String date;
    private String location;

    public Event(int id, String name, String location, String date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;

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

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + location + " - " + date;
    }
}

