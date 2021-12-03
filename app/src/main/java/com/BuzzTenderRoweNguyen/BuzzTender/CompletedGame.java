package com.BuzzTenderRoweNguyen.BuzzTender;

import java.util.Date;

public class CompletedGame {

    private String game;
    String uid;
    private String nickname;
    private double bac;
    private String result;
    private Date createdTime;

    public CompletedGame() {
    }

    public CompletedGame(String uid, String game, String nickname, double bac, String result, Date createdTime) {
        this.uid = uid;
        this.game = game;
        this.nickname = nickname;
        this.bac = bac;
        this.result = result;
        this.createdTime = createdTime;
    }


//    GETTERS
    public String getUid() { return uid; }
    public String getGame() { return game; }
    public String getNickname() { return nickname; }
    public double getBac() { return bac; }
    public String getResult() { return result; }
    public Date getCreatedTime() { return createdTime; }

//    SETTERS
    public void setGame(String game) { this.game = game; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setBac(float bac) { this.bac = bac; }
    public void setResult(String result) { this.result = result; }


}   // End Class



