package com.riontech.demoproject.dao;

/**
 * Created by Dhaval Soneji Riontech on 27/9/16.
 */
public class Dao {
    private String mTemp;
    private int mType;
    private int mLike;

    public Dao(int type, int like) {
        this.mType = type;
        this.mLike = like;
        this.mTemp = "abc";
    }

    public String getTemp() {
        return mTemp;
    }

    public int getLike() {
        return mLike;
    }

    public void setLike(int like) {
        this.mLike = like;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
