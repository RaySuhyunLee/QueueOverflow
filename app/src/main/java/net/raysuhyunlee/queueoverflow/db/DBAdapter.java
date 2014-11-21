package net.raysuhyunlee.queueoverflow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.raysuhyunlee.queueoverflow.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SuhyunLee on 14. 11. 13..
 * DBAdapter is implemented in Singleton Pattern.
 */
public class DBAdapter {
    private static DBAdapter dbAdapter;
    public static final int ERROR_TASK_ALREADY_EXISTS = -2;
    private DBOpenHelper dbOpenHelper;

    public static DBAdapter getInstance(Context context) {
        if(dbAdapter == null) {
            dbAdapter = new DBAdapter(context);
        }
        return dbAdapter;
    }

    // constructor should be private to prevent direct instantiation
    private DBAdapter(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public long insertTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase(); // get a writable sql database

        if (readTask(task, date)!=null) {
            Log.e(getClass().getName(), "same task already exists");
            return ERROR_TASK_ALREADY_EXISTS;
        }

        ContentValues values = new ContentValues();
        values.put(DBContract.TaskColumns.COLUMN_NAME_TASK_NAME, task);
        values.put(DBContract.TaskColumns.COLUMN_NAME_TASK_DATE, calendarToDatetime(date));

        long result = db.insert(DBContract.TaskColumns.TABLE_NAME, null, values);
        return result;
    }

    public ArrayList<Task> readTaskAll() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String[] projection = {
                DBContract.TaskColumns._ID,
                DBContract.TaskColumns.COLUMN_NAME_TASK_NAME,
                DBContract.TaskColumns.COLUMN_NAME_TASK_DATE
        };

        String sortOrder = DBContract.TaskColumns.COLUMN_NAME_TASK_DATE + " ASC";

        Cursor cursor = db.query(
                DBContract.TaskColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        // build an ArrayList of Task from Cursor
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Task task = new Task(cursor.getString(1),
                        datetimeToCalendar(cursor.getString(2)));
                taskArrayList.add(task);
                cursor.moveToNext();
            }
        }

        // close after use to prevent memory leak
        cursor.close();
        return taskArrayList;
    }

    public Task readTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        String[] projection = {
                DBContract.TaskColumns._ID,
                DBContract.TaskColumns.COLUMN_NAME_TASK_NAME,
                DBContract.TaskColumns.COLUMN_NAME_TASK_DATE
        };
        String selection = DBContract.TaskColumns.COLUMN_NAME_TASK_NAME + "=? AND " +
                DBContract.TaskColumns.COLUMN_NAME_TASK_DATE + "=?";
        String[] selectionArgs = {task, calendarToDatetime(date)};


        Cursor cursor = db.query(
                DBContract.TaskColumns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        Task result = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = new Task(cursor.getString(1),
                    datetimeToCalendar(cursor.getString(2)));
        }

        // close after use to prevent memory leak
        cursor.close();
        return result;
    }

    public int deleteTask(String task, Calendar date) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String selection = DBContract.TaskColumns.COLUMN_NAME_TASK_NAME + "=? AND " +
                DBContract.TaskColumns.COLUMN_NAME_TASK_DATE + "=?";
        String[] selectionArgs = {task, calendarToDatetime(date)};

        int result = db.delete(DBContract.TaskColumns.TABLE_NAME, selection, selectionArgs);
        return result;
    }

    private String calendarToDatetime(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DBContract.SQL_DATETIME_FORMAT);
        return dateFormat.format(date.getTime());
    }

    private Calendar datetimeToCalendar(String datetime) {
        Calendar task_date = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DBContract.SQL_DATETIME_FORMAT);
            Date date = dateFormat.parse(datetime);
            task_date.setTime(date);
        } catch(ParseException e) {
            e.printStackTrace();
            Log.e(getClass().getName(), "Date Format doesn't matches the datetime data." +
                    " Maybe the database has been crashed");
            // TODO handle error situation
        }
        return task_date;
    }


    // DBOpenHelper Class
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
