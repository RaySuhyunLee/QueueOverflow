package net.raysuhyunlee.queueoverflow;

import android.os.Bundle;

/**
 * Created by Suhyun Lee on 2014-11-14.
 */
public class AddTaskActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setToolbar();
    }
}
