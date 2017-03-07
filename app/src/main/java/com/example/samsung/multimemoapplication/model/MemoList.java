package com.example.samsung.multimemoapplication.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-15.
 */

public class MemoList implements Serializable {
    private int id;
    private String curDate;
    private String memo;

    public MemoList(String curDate, String memo) {
        this.curDate = curDate;
        this.memo = memo;
    }

    public MemoList(int id, String curDate, String memo) {
        this.id = id;
        this.curDate = curDate;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
