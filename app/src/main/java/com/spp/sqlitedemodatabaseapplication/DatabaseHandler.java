package com.spp.sqlitedemodatabaseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "VisitorDB";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //  Table names
    private static final String TABLE_VISITORS = "visitors";

    // User Table Columns names
    private static final String KEY_VISITOR_ID = "visitor_id";
    private static final String KEY_VISITOR_NAME = "visitor_name";
    private static final String KEY_PROGRAM_LANGUAGE = "program_language";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE_NUMBER= "mobile_number";
    private static final String KEY_DATE_TIME= "date_time";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_VISITOR_TABLE = "CREATE TABLE " + TABLE_VISITORS + "("
                + KEY_VISITOR_ID + " INTEGER Primary key,"
                + KEY_VISITOR_NAME + " TEXT,"
                + KEY_PROGRAM_LANGUAGE + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_MOBILE_NUMBER + " TEXT,"
                + KEY_DATE_TIME + " TEXT" +")";
        sqLiteDatabase.execSQL(CREATE_VISITOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITORS);
    }

    public Long insertVisitor (Visitor visitor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VISITOR_NAME, visitor.getName());
        contentValues.put(KEY_PROGRAM_LANGUAGE,visitor.getLanguage());
        contentValues.put(KEY_EMAIL,visitor.getEmail());
        contentValues.put(KEY_MOBILE_NUMBER,visitor.getNumber());
        contentValues.put(KEY_DATE_TIME,visitor.getDate());

        Long result= db.insert(TABLE_VISITORS, null, contentValues);
        return result;
    }

    public ArrayList<Visitor> getAllVisitors() {
        ArrayList<Visitor> visitors = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VISITORS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Visitor visitor = new Visitor();
                visitor.setId(cursor.getInt(cursor.getColumnIndex(KEY_VISITOR_ID)));
                visitor.setName(cursor.getString(cursor.getColumnIndex(KEY_VISITOR_NAME)));
                visitor.setLanguage(cursor.getString(cursor.getColumnIndex(KEY_PROGRAM_LANGUAGE)));
                visitor.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                visitor.setNumber(cursor.getString(cursor.getColumnIndex(KEY_MOBILE_NUMBER)));
                visitor.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME)));
                visitors.add(visitor);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return visitors;
    }

    public int deleteVisitor (String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result= db.delete(TABLE_VISITORS, KEY_VISITOR_ID+"=?",new String[]{id});
        return result;
    }
}

