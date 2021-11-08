package com.BuzzTenderRoweNguyen.BuzzTender;

class User {

    private String gender;
    private int weight;
    private int age;
//    private String userID;

    public User(){}

    public User( String gender, int weight, int age){
        this.gender = gender;
        this.weight = weight;
        this.age = age;
    }

    //    Getters
//    public String getUserID() { return userID; }
    public String getGender(){
        return gender;
    }
    public int getWeight(){
        return weight;
    }
    public int getAge(){
        return age;
    }

    //    Setters
    public void setGender(String gender){ this.gender = gender; }
    public void setWeight (int weight) {this.weight = weight;}
    public void setAge(int age) {this.age = age; }



}
