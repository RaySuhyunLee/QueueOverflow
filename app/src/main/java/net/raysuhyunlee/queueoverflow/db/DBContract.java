package net.raysuhyunlee.queueoverflow.db;

import android.provider.BaseColumns;

/**
 * Created by SuhyunLee on 14. 11. 11..
 */
public class DBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {}

    public static abstract class Task implements BaseColumns {
        public static final String TABLE_NAME = "table_task";
        public static final String COLUMN_NAME_TASK = "name";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
