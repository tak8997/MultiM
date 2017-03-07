package com.example.samsung.multimemoapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.samsung.multimemoapplication.common.MyApplication;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-01-23.
 */

public final class MultiMemoDBHelper extends SQLiteOpenHelper {
    public static MultiMemoDBHelper multiMemoDBHelper;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MultiMemo";

    //테이블 이름
    public static final String MEMO_TABLE = "MultiMemo";

    //테이블 컬럼
    public static final String _ID = "id";
    public static final String MEMO_CONTENT_TEXT = "content_text";
    public static final String MEMO_CREATE_DATE = "create_date";
    public static final String MEMO_INPUT_DATE = "input_date";

    public static MultiMemoDBHelper getInstance() {
        if(multiMemoDBHelper == null)
            multiMemoDBHelper = new MultiMemoDBHelper();

        return multiMemoDBHelper;
    }

    private MultiMemoDBHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        ** MEMO_TABLE
        */

        // 테이블 생성
        String CREATE_SQL =
            "CREATE TABLE " + MEMO_TABLE + " (" +
                    _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    MEMO_CREATE_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    MEMO_CONTENT_TEXT + " TEXT DEFAULT ' ' " +
                    ")";
        db.execSQL(CREATE_SQL);
        Log.d("tableCreation", "table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        // 테이블이 있으면 제거
        String DELETE_SQL = "DROP TABLE IF EXISTS " + MEMO_TABLE;
        db.execSQL(DELETE_SQL);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new memo
    public void addMemo(MemoList memoList) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEMO_CREATE_DATE, memoList.getCurDate());
        values.put(MEMO_CONTENT_TEXT, memoList.getMemo());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MEMO_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single memo
    public MemoList getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MEMO_TABLE, new String[] { _ID,
                        MEMO_CREATE_DATE, MEMO_CONTENT_TEXT }, _ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MemoList memoList = new MemoList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return memoList;
    }

    // Getting All Memos
    public List<MemoList> getAllMemoLists() {
        List<MemoList> memoLists = new ArrayList<MemoList>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MEMO_TABLE;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String date = cursor.getString(1);
                String memo = cursor.getString(2);

                MemoList memoList = new MemoList(id, date, memo);
                // Adding contact to list
                memoLists.add(memoList);
            } while (cursor.moveToNext());
        }

        // return memo list
        return memoLists;
    }

        // Updating single memo
        public int updateMemo(MemoList memoList) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(MEMO_CREATE_DATE, memoList.getCurDate());
            values.put(MEMO_CONTENT_TEXT, memoList.getMemo());

            // updating row
            return db.update(MEMO_TABLE, values, _ID + " = ?",
                    new String[] { String.valueOf(memoList.getId()) });
        }

        // Deleting single memo
        public void deleteMemo(MemoList memoList) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(MEMO_TABLE, _ID + " = ?",
                    new String[] { String.valueOf(memoList.getId()) });
            db.close();
        }


//        // Getting contacts Count
//        public int getContactsCount() {
//            String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(countQuery, null);
//            cursor.close();
//
//            // return count
//            return cursor.getCount();
//        }
}
