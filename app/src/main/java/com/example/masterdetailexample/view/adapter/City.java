package com.example.masterdetailexample.view.adapter;

/**
 * Created by tamirnoach on 30/10/2017.
 */

public class City {
    private String name;

    private String id;

    public City(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public static City create(String name, String id) {
        return  new City (name,id);
    }
}
