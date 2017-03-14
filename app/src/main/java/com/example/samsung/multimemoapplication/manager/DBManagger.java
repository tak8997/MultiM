package com.example.samsung.multimemoapplication.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.samsung.multimemoapplication.common.MyApplication;
import com.example.samsung.multimemoapplication.model.MemoList;
import com.example.samsung.multimemoapplication.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-01-23.
 */

public final class DBManagger extends SQLiteOpenHelper {
    public static DBManagger DBManagger;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MultiMemo";

    /*
    ** 테이블 이름
    */
    private static final String MEMO_TABLE = "MultiMemo";
    private static final String USER_ACCOUNT_TABLE = "UserAccount";

    /*
    ** 테이블 컬럼
    */
    // 공통
    private static final String _ID = "id";
    private static final String USER_ID = "id";

    // 메모 테이블 컬럼
    private static final String MEMO_CONTENT_TEXT = "content_text";
    private static final String MEMO_CREATE_DATE = "create_date";
    private static final String MEMO_INPUT_DATE = "input_date";

    // 유저 테이블 컬럼
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    public static DBManagger getInstance() {
        if(DBManagger == null)
            DBManagger = new DBManagger();

        return DBManagger;
    }

    private DBManagger() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "onCreate");
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
        Log.d("table", "Memo table created");

        /*
        ** USER_TABLE
        */
        // 테이블 생성
        CREATE_SQL =
                "CREATE TABLE " + USER_ACCOUNT_TABLE + " (" +
                        USER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        USER_EMAIL + " TEXT, " +
                        USER_PASSWORD + " TEXT " +
                        ")";
        db.execSQL(CREATE_SQL);
        Log.d("table", "User table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBHelper", "onUpgrade");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        // 테이블이 있으면 제거
        String DELETE_SQL = "DROP TABLE IF EXISTS " + MEMO_TABLE;
        db.execSQL(DELETE_SQL);

        DELETE_SQL = "DROP TABLE IF EXISTS " + USER_ACCOUNT_TABLE;
        db.execSQL(DELETE_SQL);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    /**
     * About MEMO TABLE
     */

    // Adding new memo
    public void addMemo(MemoList memoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEMO_CREATE_DATE, memoList.getCurDate());
        values.put(MEMO_CONTENT_TEXT, memoList.getMemo());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MEMO_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single memo
    public MemoList getMemo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MEMO_TABLE, new String[] { _ID,
                        MEMO_CREATE_DATE, MEMO_CONTENT_TEXT }, _ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MemoList memoList = new MemoList(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return memo
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

//        // Getting Memos Count
//        public int getMemosCount() {
//            String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(countQuery, null);
//            cursor.close();
//
//            // return count
//            return cursor.getCount();
//        }

    /**
     * About UERR ACOUNT TABLE
     */

    public void insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insertUser", "success");

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(USER_EMAIL, email);
        newValues.put(USER_PASSWORD,password);

        // Insert the row into your table
        db.insert(USER_ACCOUNT_TABLE, null, newValues);
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(USER_ACCOUNT_TABLE, _ID + "=?",
                new String[]{String.valueOf(user.getId())}) ;
        db.close();
    }

    // getting single user
    public User getUser(String email) {
        Log.d("DBHelper", "getUser");
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + USER_ACCOUNT_TABLE + " WHERE " + USER_EMAIL + " =?";
        String[]  selectionArgs = new String[]{email};
        Cursor cursor = db.rawQuery(query, selectionArgs);
//        Cursor cursor = db.query(USER_ACCOUNT_TABLE, null, USER_EMAIL + "=? ", new String[]{email}, null, null, null);

        // user email Not Exist
        if(cursor.getCount()<1) {
            cursor.close();
            return null;
        }

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return memo
        return user;

//        cursor.moveToFirst();
//        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
//        cursor.close();
//
//        return password;
    }

//    public void  updateEntry(String userName,String password)
//    {
//        // Define the updated row content.
//        ContentValues updatedValues = new ContentValues();
//        // Assign values for each row.
//        updatedValues.put("USERNAME", userName);
//        updatedValues.put("PASSWORD",password);
//
//        String where="USERNAME = ?";
//        db.update("LOGIN",updatedValues, where, new String[]{userName});
//    }
}
