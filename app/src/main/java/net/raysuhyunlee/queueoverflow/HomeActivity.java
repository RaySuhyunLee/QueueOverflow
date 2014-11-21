package net.raysuhyunlee.queueoverflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import net.raysuhyunlee.queueoverflow.db.DBAdapter;


public class HomeActivity extends BaseActivity {
    public static final int REQUEST_ADD_PLAN_ACTIVITY = 100;

    private TaskListAdapter taskListAdapter;
    private DBAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeToolbar();
        
        ImageButton button_add = (ImageButton)findViewById(R.id.button_add);
        button_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, REQUEST_ADD_PLAN_ACTIVITY);
            }
        });

        dbAdapter = DBAdapter.getInstance(this);
        taskListAdapter = new TaskListAdapter(this, dbAdapter.readTaskAll());
        ListView listView_task = (ListView)findViewById(R.id.listView_task);
        listView_task.setAdapter(taskListAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD_PLAN_ACTIVITY
                && resultCode == RESULT_OK) {

        }
    }
}
