package app.dicky.meetthearrogance.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.dicky.meetthearrogance.R;

/**
 * copyright© www.pemt.com.cn
 * Created by eng005 on 2016/8/22
 */


public class TimeTestActivity extends Activity {
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_test);
    }

    @Override
    protected void onResume() {
        init();
        TextView tv = (TextView) findViewById(R.id.timeText);
        tv.setText(str);
        super.onResume();
    }

    void init(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss  ");
        Date curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);
        Calendar cal = Calendar.getInstance();
    }
}
