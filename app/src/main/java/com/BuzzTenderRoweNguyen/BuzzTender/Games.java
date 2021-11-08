package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;

public class Games {

    private String name;
    private String description;

    public Games(){}

    public Games( String name, String description){
        this.name = name;
        this.description = description;
    }

    //    Getters
//    public String getUserID() { return userID; }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }

    //    Setters
    public void setName(String name){ this.name = name; }
    public void setDescription (String description) {this.description = description;}

    @NonNull
    @Override
    public String toString() {
        return this.name + " " + this.description;
    }
}
