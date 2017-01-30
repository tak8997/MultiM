package com.example.samsung.multimemoapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.samsung.multimemoapplication.model.MemoList;

import static com.example.samsung.multimemoapplication.database.MultiMemoDB.SQL_CREATE_TABLE;
import static com.example.samsung.multimemoapplication.database.MultiMemoDB.SQL_DELETE_TABLE;

/**
 * Created by SAMSUNG on 2017-01-23.
 */

public final class MultiMemoDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MultiMemo";

    public MultiMemoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_CREATE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new memo
    void addMemo(MemoList contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(MultiMemoDB.FeedMemo.COLUMN_NAME_IMAGE, title);
//        values.put(MultiMemoDB.FeedMemo.COLUMN_NAME_CURDATE, subtitle);
//        values.put(MultiMemoDB.FeedMemo.COLUMN_NAME_MEMO, subtitle);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MultiMemoDB.FeedMemo.TABLE_MEMO, null, values);
        db.close(); // Closing database connection
    }

//        // Getting single contact
//        Contact getContact(int id) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                            KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                    new String[] { String.valueOf(id) }, null, null, null, null);
//            if (cursor != null)
//                cursor.moveToFirst();
//
//            Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                    cursor.getString(1), cursor.getString(2));
//            // return contact
//            return contact;
//        }
//
//        // Getting All Contacts
//        public List<Contact> getAllContacts() {
//            List<Contact> contactList = new ArrayList<Contact>();
//            // Select All Query
//            String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    Contact contact = new Contact();
//                    contact.setID(Integer.parseInt(cursor.getString(0)));
//                    contact.setName(cursor.getString(1));
//                    contact.setPhoneNumber(cursor.getString(2));
//                    // Adding contact to list
//                    contactList.add(contact);
//                } while (cursor.moveToNext());
//            }
//
//            // return contact list
//            return contactList;
//        }
//
//        // Updating single contact
//        public int updateContact(Contact contact) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_NAME, contact.getName());
//            values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//            // updating row
//            return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                    new String[] { String.valueOf(contact.getID()) });
//        }
//
//        // Deleting single contact
//        public void deleteContact(Contact contact) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                    new String[] { String.valueOf(contact.getID()) });
//            db.close();
//        }
//
//
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
