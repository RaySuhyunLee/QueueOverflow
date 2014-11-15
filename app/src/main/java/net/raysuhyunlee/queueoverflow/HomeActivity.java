package net.raysuhyunlee.queueoverflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import net.raysuhyunlee.queueoverflow.db.DBAdapter;


public class HomeActivity extends BaseActivity {
    public static final int REQUEST_ADD_PLAN_ACTIVITY = 100;

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

        dbAdapter = new DBAdapter(this);
    }
}
