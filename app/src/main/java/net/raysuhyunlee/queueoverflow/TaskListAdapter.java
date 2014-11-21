package net.raysuhyunlee.queueoverflow;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
public class TaskListAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Task> taskArrayList;

    public TaskListAdapter(Context context, int resource, ArrayList<Task> taskArrayList) {
        super(context, resource, taskArrayList);
        this.context = context;
        this.taskArrayList = taskArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // inflate layout
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_task, null);
            TextView textView_taskName = (TextView)convertView.findViewById(R.id.textView_taskName);
            TextView textView_timeLeft = (TextView)convertView.findViewById(R.id.textView_timeLeft);
            TextView textView_daysOrHours
                    = (TextView)convertView.findViewById(R.id.textView_daysOrHours);

            Task task = taskArrayList.get(position);

            // set task name
            textView_taskName.setText(task.getTaskName());

            // calculate time left
            Calendar today = Calendar.getInstance();
            Calendar task_date = task.getTaskDate();
            Long timeDifference = task_date.getTimeInMillis()
                    - today.getTimeInMillis();
            int daysLeft = (int)(timeDifference / (24 * 3600 * 1000));
            if (daysLeft > 0) {
                textView_timeLeft.setText(String.valueOf(daysLeft));
                textView_daysOrHours.setText(context.getString(R.string.daysLeft));
            } else {
                int hoursLeft = (int)(timeDifference / (3600 * 1000));
                textView_timeLeft.setText(String.valueOf(hoursLeft));
                textView_daysOrHours.setText(context.getString(R.string.hoursLeft));
            }

        }
        return convertView;
    }
}
