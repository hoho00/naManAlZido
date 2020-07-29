package com.hackerton.googlemap.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewDbHelper extends SQLiteOpenHelper {
    private static ReviewDbHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Reviews.db";

    //DB 생성 SQL문
    private static final String SQL_CREATE_ENTRIES;
    static {
        SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER NOT NULL)",
                ReviewContract.ReviewEntry.TABLE_NAME,
                ReviewContract.ReviewEntry._ID,
                ReviewContract.ReviewEntry.COLUMN_NAME_NAME,
                ReviewContract.ReviewEntry.COLUMN_NAME_ADDRESS,
                ReviewContract.ReviewEntry.COLUMN_NAME_CONTENTS,
                ReviewContract.ReviewEntry.COLUMN_NAME_TIMESTAMP,
                ReviewContract.ReviewEntry.COLUMN_NAME_SCORE);
    }

    //DB 삭제 SQL문
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + ReviewContract.ReviewEntry.TABLE_NAME;

    //팩토리 메서드
    public static synchronized ReviewDbHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new ReviewDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private ReviewDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
