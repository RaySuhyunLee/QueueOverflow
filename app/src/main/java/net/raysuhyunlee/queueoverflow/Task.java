package net.raysuhyunlee.queueoverflow;

import java.util.Calendar;

/**
 * Created by Suhyun Lee on 2014-11-21.
 */
public class Task {
    private String task_name;
    private Calendar task_date;

    public Task(String task_name, Calendar task_date) {
        this.task_name = task_name;
        this.task_date = task_date;
    }

    public void setTaskName(String task_name) {
        this.task_name = task_name;
    }

    public void setTaskDate(Calendar task_date) {
        this.task_date = task_date;
    }

    public String getTaskName() {
        return task_name;
    }

    public Calendar getTaskDate() {
        return (Calendar)task_date.clone();
    }
}
