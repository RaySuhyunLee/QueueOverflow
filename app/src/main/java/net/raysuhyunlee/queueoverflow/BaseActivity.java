package net.raysuhyunlee.queueoverflow;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by SuhyunLee on 14. 11. 10..
 */

public class BaseActivity extends ActionBarActivity {

    public void setToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

        }
    }
}