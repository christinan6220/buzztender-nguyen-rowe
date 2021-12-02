package com.BuzzTenderRoweNguyen.BuzzTender;

public class CompletedGame {

    private String game;
    String uid;
    private String nickname;
    private float bac;
    private String result;

    public CompletedGame() {
    }

    public CompletedGame(String uid, String game, String nickname, float bac, String result) {
        this.uid = uid;
        this.game = game;
        this.nickname = nickname;
        this.bac = bac;
        this.result = result;
    }


//    GETTERS
    public String getUid() { return uid; }
    public String getGame() { return game; }
    public String getNickname() { return nickname; }
    public float getBac() { return bac; }
    public String getResult() { return result; }

//    SETTERS
    public void setGame(String game) { this.game = game; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setBac(float bac) { this.bac = bac; }
    public void setResult(String result) { this.result = result; }


}   // End Class



