package com.xyzlf.area.selector;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by j-zhanglifeng-ncf on 2016/9/1.
 */

public class AreaData implements Serializable {

    private String name;
    private ArrayList<String> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

}
