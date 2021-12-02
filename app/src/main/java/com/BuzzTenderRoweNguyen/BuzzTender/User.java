package com.BuzzTenderRoweNguyen.BuzzTender;

class User {

    private String nickname, gender;
    private int weight;
    private int age;
//    private String userID;

    public User(){}

    public User(String nickname, String gender, int weight, int age){
        this.nickname = nickname;
        this.gender = gender;
        this.weight = weight;
        this.age = age;
    }

    //    Getters
//    public String getUserID() { return userID; }
    public String getNickname() { return nickname; }
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
    public void setNickname (String nickname) { this.nickname = nickname; }
    public void setGender(String gender){ this.gender = gender; }
    public void setWeight (int weight) { this.weight = weight; }
    public void setAge(int age) { this.age = age; }



}
