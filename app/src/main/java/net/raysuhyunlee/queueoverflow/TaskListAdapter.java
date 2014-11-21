package net.raysuhyunlee.queueoverflow;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import net.raysuhyunlee.queueoverflow.db.DBContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SuhyunLee on 14. 11. 17..
 */
public class TaskListAdapter extends CursorAdapter {
    Context context;

    public TaskListAdapter(Context context, Cursor cursor) {
        super(context, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.context = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView_taskName = (TextView)view.findViewById(R.id.textView_taskName);
        TextView textView_timeLeft = (TextView)view.findViewById(R.id.textView_timeLeft);
        TextView textView_daysOrHours
                = (TextView)view.findViewById(R.id.textView_daysOrHours);

        // set task name
        textView_taskName.setText(cursor.getString(1));

        // calculate time left
        Calendar today = Calendar.getInstance();
        Calendar task_date = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DBContract.SQL_DATETIME_FORMAT);
            Date date = dateFormat.parse(cursor.getString(2));
            task_date.setTime(date);
        } catch(ParseException e) {
            e.printStackTrace();
            Log.e(getClass().getName(), "Wrong dateFormat. Maybe the database has crashed");
            // TODO handle error situation
            return;
        }
        Long timeDifference = task_date.getTimeInMillis()
                - today.getTimeInMillis();
        int daysLeft = (int)(timeDifference / (24 * 3600 * 1000));
        if (daysLeft > 0) {
            textView_timeLeft.setText(daysLeft);
            textView_daysOrHours.setText(context.getString(R.string.daysLeft));
        } else {
            int hoursLeft = (int)(timeDifference / (3600 * 1000));
            textView_timeLeft.setText(hoursLeft);
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.list_task, parent);
    }
/*
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // inflate layout
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_task, parent);
            TextView textView_taskName = (TextView)convertView.findViewById(R.id.textView_taskName);
            TextView textView_timeLeft = (TextView)convertView.findViewById(R.id.textView_timeLeft);
            TextView textView_daysOrHours
                    = (TextView)convertView.findViewById(R.id.textView_daysOrHours);



        }
        return convertView;
    }*/
}
