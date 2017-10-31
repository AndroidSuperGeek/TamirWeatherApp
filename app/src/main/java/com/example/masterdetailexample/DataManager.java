package com.example.masterdetailexample;

import com.example.masterdetailexample.view.adapter.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tamirnoach on 30/10/2017.
 */

public  class DataManager {
    public static List<City> getCities() {
        List<City> cities = new ArrayList<City> ();


//            Vancouver: 6173331 Moscow: 524901 Bangkok: 1609350 Johannesburg: 993800 Tunis: 2464470
//        Manila: 1701668

        cities.add (City.create("London","2643743"));
        cities.add (City.create("Tel-Aviv","293396"));
        cities.add (City.create("New-York", "5128581"));
        cities.add (City.create("Brussels", "2800866"));
        cities.add (City.create("Barcelona" ,"3128760"));

        cities.add (City.create("Paris", "2988507"));
        cities.add (City.create("Tokyo","1850147"));
        cities.add (City.create(" Beijing","1816670"));

        cities.add (City.create("Sydney","147714"));

        cities.add (City.create("Buenos-Aires" ,"3432043"));

        cities.add (City.create("Miami" ,"41641383"));

        return cities;
    }
}
