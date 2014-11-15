package net.raysuhyunlee.queueoverflow;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by SuhyunLee on 14. 11. 10..
 */

public class BaseActivity extends ActionBarActivity {

    public Toolbar initializeToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getTitle());
        }
        return toolbar;
    }
}