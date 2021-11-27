package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;

public class Games {

    private String name;
    private String description;
    private float BAC;

    public Games(){}

    public Games( String name, String description, float BAC){
        this.name = name;
        this.description = description;
        this.BAC = BAC;

    }

    //    Getters
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public float getBAC() { return BAC; }



    //    Setters
    public void setName(String name){ this.name = name; }
    public void setDescription (String description) {this.description = description;}
    public void setBAC(float BAC) { this.BAC = BAC; }


    @NonNull
    @Override
    public String toString() {
        return this.name + " " + this.description;
    }
}
