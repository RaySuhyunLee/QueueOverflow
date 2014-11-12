package net.raysuhyunlee.queueoverflow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Suhyun Lee on 2014-11-12.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "queue_overflow.db";
    public static final int DATABASE_VERSION = 1;   // from v0.1~

    public static final String SQL_CREATE_TASK =
                    "CREATE_TABLE " + DBContract.Task.TABLE_NAME + "(" +
                    DBContract.Task.COLUMN_NAME_TASK + " VARCHAR(30)," +
                    DBContract.Task.COLUMN_NAME_DATE + " DATE);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASK);
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
