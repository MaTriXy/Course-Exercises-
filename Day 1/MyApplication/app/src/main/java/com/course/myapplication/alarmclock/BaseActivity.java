package com.course.myapplication.alarmclock;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.course.myapplication.R;
import com.course.myapplication.alarmclock.preferences.AlarmPreferencesActivity;
import com.course.myapplication.alarmclock.service.AlarmServiceBroadcastReciever;

import java.lang.reflect.Field;

public abstract class BaseActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url = null;
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_item_new:
                Intent newAlarmIntent = new Intent(this, AlarmPreferencesActivity.class);
                startActivity(newAlarmIntent);
                break;
            case R.id.menu_item_website:
                url = "http://www.tikalk.com";
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Couldn't launch the website", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }
}
