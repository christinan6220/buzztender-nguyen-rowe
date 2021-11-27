package com.BuzzTenderRoweNguyen.BuzzTender;

public class CompletedGame {

    private String game;
    private String uid;
    private float bac;
    private String result;

    public CompletedGame() {
    }

    public CompletedGame(String game, String uid, float bac, String result) {
        this.game = game;
        this.uid = uid;
        this.bac = bac;
        this.result = result;
    }


//    GETTERS
    public String getGame() { return game; }

    public String getUid() { return uid; }

    public float getBac() { return bac; }

    public String getResult() { return result; }

//    SETTERS
    public void setGame(String game) { this.game = game; }

    public void setUid(String uid) { this.uid = uid; }

    public void setBac(float bac) { this.bac = bac; }

    public void setResult(String result) { this.result = result; }


}   // End Class



