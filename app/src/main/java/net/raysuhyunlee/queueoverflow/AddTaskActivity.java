package net.raysuhyunlee.queueoverflow;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;

import net.raysuhyunlee.queueoverflow.db.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Suhyun Lee on 2014-11-14.
 */
public class AddTaskActivity extends BaseActivity {
    DeadlineManager deadlineManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        deadlineManager = new DeadlineManager();

        setToolbar();

        TextView textView_date = (TextView)findViewById(R.id.textView_date);
        TextView textView_time = (TextView)findViewById(R.id.textView_time);
        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        textView_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        resetTextViewDate();
        resetTextViewTime();
    }

    public void setToolbar() {
        Toolbar toolbar = initializeToolbar();
        toolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.action_save:
                        /*DBAdapter dbAdapter = new DBAdapter(AddTaskActivity.this);
                        EditText editText_taskName = (EditText)findViewById(R.id.editText_taskName);
                        dbAdapter.insertTask(editText_taskName.getText().toString(),
                                deadlineManager.getDeadline());*/
                        finish();
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.activity_add_task);
    }

    public void showDatePickerDialog() {
        View dialog_date_picker = View.inflate(this, R.layout.dialog_date_picker, null);
        {
            DatePicker datePicker = (DatePicker) dialog_date_picker.findViewById(R.id.datePicker);
            Calendar deadline = deadlineManager.getDeadline();
            datePicker.init(deadline.get(Calendar.YEAR),
                    deadline.get(Calendar.MONTH),
                    deadline.get(Calendar.DAY_OF_MONTH),
                    null);
        }

        new MaterialDialog.Builder(this)
                .customView(dialog_date_picker)
                .callback(new MaterialDialog.SimpleCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        DatePicker datePicker = (DatePicker)dialog.getCustomView().findViewById(R.id.datePicker);
                        deadlineManager.setDeadlineDate(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth());
                        resetTextViewDate();
                    }
                })
                .build()
                .show();
    }

    public void showTimePickerDialog() {
        View dialog_time_picker = View.inflate(this, R.layout.dialog_time_picker, null);
        {
            TimePicker timePicker = (TimePicker) dialog_time_picker.findViewById(R.id.timePicker);
            Calendar deadline = deadlineManager.getDeadline();
            timePicker.setCurrentHour(deadline.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(deadline.get(Calendar.MINUTE));
        }
        new MaterialDialog.Builder(this)
                .customView(dialog_time_picker)
                .callback(new MaterialDialog.SimpleCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        TimePicker timePicker = (TimePicker)dialog.getCustomView().findViewById(R.id.timePicker);
                        deadlineManager.setDeadlineTime(timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        resetTextViewTime();
                    }
                })
                .build()
                .show();
    }

    public void resetTextViewDate() {
        TextView textView_date = (TextView)findViewById(R.id.textView_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
        textView_date.setText(dateFormat.format(deadlineManager.getDeadline().getTime()));
    }

    public void resetTextViewTime() {
        TextView textView_time = (TextView)findViewById(R.id.textView_time);
        SimpleDateFormat timeFormat = new SimpleDateFormat(getString(R.string.time_format));
        textView_time.setText(timeFormat.format(deadlineManager.getDeadline().getTime()));
    }

    private class DeadlineManager {
        private Calendar deadline;
        public DeadlineManager() {
            deadline = Calendar.getInstance();
        }
        public void setDeadlineDate(int year, int month, int dayOfMonth) {
            deadline.set(year, month, dayOfMonth);
        }
        public void setDeadlineTime(int hourOfDay, int minute) {
            deadline.set(Calendar.HOUR_OF_DAY, hourOfDay);
            deadline.set(Calendar.MINUTE, minute);
        }
        public Calendar getDeadline() {
            return (Calendar)deadline.clone();
        }
    }
}
