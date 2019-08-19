
package com.example.umbrella.model;

import java.util.ArrayList;
import java.util.List;

public class Current {

    public CurrentCoord coord;
    public List<CurrentWeather> weather = new ArrayList<CurrentWeather>();
    public String base;
    public CurrentMain main;
    public int visibility;
    public CurrentWind wind;
    public CurrentClouds clouds;
    public int dt;
    public CurrentSys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

}
