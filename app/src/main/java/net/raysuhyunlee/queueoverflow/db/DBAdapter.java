package net.raysuhyunlee.queueoverflow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SuhyunLee on 14. 11. 13..
 */
public class DBAdapter {
    public static final int ERROR_TASK_ALREADY_EXISTS = -2;
    private DBOpenHelper dbOpenHelper;
    public DBAdapter(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public long insertTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase(); // get a writable sql database

        if (readTask(task, date).getCount() > 0) {
            Log.e(getClass().getName(), "identical task already exists");
            return ERROR_TASK_ALREADY_EXISTS;
        }

        ContentValues values = new ContentValues();
        values.put(DBContract.Task.COLUMN_NAME_TASK_NAME, task);
        values.put(DBContract.Task.COLUMN_NAME_TASK_DATE, calendarToDatetime(date));

        return db.insert(DBContract.Task.TABLE_NAME, null, values);
    }

    public Cursor readTaskAll() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Task._ID,
                DBContract.Task.COLUMN_NAME_TASK_NAME,
                DBContract.Task.COLUMN_NAME_TASK_DATE
        };

        String sortOrder = DBContract.Task.COLUMN_NAME_TASK_DATE + "ASC";

        return db.query(
                DBContract.Task.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    public Cursor readTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Task._ID,
                DBContract.Task.COLUMN_NAME_TASK_NAME,
                DBContract.Task.COLUMN_NAME_TASK_DATE
        };
        String selection = DBContract.Task.COLUMN_NAME_TASK_NAME + "=? AND " +
                DBContract.Task.COLUMN_NAME_TASK_DATE + "=?";
        String[] selectionArgs = {task, calendarToDatetime(date)};

        return db.query(
                DBContract.Task.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public int deleteTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String selection = DBContract.Task.COLUMN_NAME_TASK_NAME + "=? AND " +
                DBContract.Task.COLUMN_NAME_TASK_DATE + "=?";
        String[] selectionArgs = {task, calendarToDatetime(date)};

        return db.delete(DBContract.Task.TABLE_NAME, selection, selectionArgs);
    }

    private String calendarToDatetime(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return dateFormat.format(date.getTime());
    }

    private class DBOpenHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "queue_overflow.db";
        public static final int DATABASE_VERSION = 1;   // from v0.1~


        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBContract.SQL_CREATE_TASK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // do nothing since it's version 1 yet!
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // same as onUpgrade now.
        }
    }
}
