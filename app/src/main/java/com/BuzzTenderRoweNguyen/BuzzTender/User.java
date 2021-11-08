package com.BuzzTenderRoweNguyen.BuzzTender;

class User {

    private String gender;
    private int weight;
    private int age;

    public User(){}

    public User(String gender, int weight, int age){
        this.gender = gender;
        this.weight = weight;
        this.age = age;
    }

    public String getGender(){
        return gender;
    }
    public int getWeight(){
        return weight;
    }
    public int getAge(){
        return age;
    }
}
