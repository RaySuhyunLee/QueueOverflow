package net.raysuhyunlee.queueoverflow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by SuhyunLee on 14. 11. 11..
 */
public class DBContract {

    public static final String SQL_CREATE_TASK =
            "CREATE_TABLE " + Task.TABLE_NAME + "(" +
                    Task.COLUMN_NAME_TASK_NAME + " VARCHAR(30)," +
                    Task.COLUMN_NAME_TASK_DATE + " DATETIME);";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {}

    public static abstract class Task implements BaseColumns {
        public static final String TABLE_NAME = "table_task";
        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_DATE = "task_date";

    }
}