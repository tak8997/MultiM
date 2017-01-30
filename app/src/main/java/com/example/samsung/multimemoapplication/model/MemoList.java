package com.example.samsung.multimemoapplication.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-15.
 */

public class MemoList {
    int image;
    Date curDate;
    String memo;

    public MemoList(int image, Date curDate,String memo) {
        this.curDate = curDate;
        this.image = image;
        this.memo = memo;
    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
