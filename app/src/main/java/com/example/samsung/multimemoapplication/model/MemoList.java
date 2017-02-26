package com.example.samsung.multimemoapplication.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-15.
 */

public class MemoList {
    private String curDate;
    private String memo;

    public MemoList(String curDate, String memo) {
        this.curDate = curDate;
        this.memo = memo;
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
