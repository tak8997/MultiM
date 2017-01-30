package com.example.samsung.multimemoapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by SAMSUNG on 2017-01-24.
 */

public class MultiMemoDB {
    private Context context;
    private static MultiMemoDB multiMemoDB; //싱글톤 인스턴스
    private MultiMemoDBHelper multiMemoDBHelper; //데이터베이스 Helper
    private SQLiteDatabase database;

    private MultiMemoDB(Context context) {
        this.context = context;
    }

    //싱글톤 작업
    public static MultiMemoDB getInstance(Context context) {
        if(multiMemoDB == null)
            multiMemoDB = new MultiMemoDB(context);

        return multiMemoDB;
    }

    //데이터베이스 열기
    public boolean open() {
        multiMemoDBHelper = new MultiMemoDBHelper(context);
        database = multiMemoDBHelper.getWritableDatabase(); //??????

        return true;
    }

    //데이터베이스 닫기
    public void close() {
        database.close();

        database = null;
    }

    /* Inner class that defines the table contents */
    public static class FeedMemo implements BaseColumns {
        //테이블 이름
        public static final String TABLE_MEMO = "MultiMemo";

        //테이블 컬럼
        public static final String INPUT_DATE = "input_date";
        public static final String CONTENT_TEXT = "content_text";
        public static final String ID_PHOTO = "id_photo";
        public static final String ID_VIDEO = "id_video";
        public static final String ID_VOICE = "id_voice";
        public static final String ID_HANDWRITING = "id_handwriting";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + FeedMemo.TABLE_MEMO + "(" +
                    FeedMemo._ID + " INTEGER PRIMARY KEY, " +
                    FeedMemo.INPUT_DATE + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    FeedMemo.CONTENT_TEXT + "TEXT DEFAULT '', " +
                    FeedMemo.ID_PHOTO + "INTEGER, " +
                    FeedMemo.ID_VIDEO + "INTEGER, " +
                    FeedMemo.ID_VOICE + "INTEGER, " +
                    FeedMemo.ID_HANDWRITING + "INTEGER, " +
                    "CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                    ")";

    public static final String SQL_DELETE_TABLE=
            "DROP TABLE IF EXISTS " + FeedMemo.TABLE_MEMO;
}
