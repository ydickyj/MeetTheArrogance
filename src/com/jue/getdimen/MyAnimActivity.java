package com.jue.getdimen;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.apache.commons.logging.Log;

import java.io.IOException;

/**
 * Created by dicky on 2016/6/22.
 *
 */
public class MyAnimActivity extends Activity {

    MediaPlayer mPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        Button btnPlay = (Button)findViewById(R.id.play);
        Uri uri = RingtoneManager.getDefaultUri(1);
        android.util.Log.e("硬晨哦哦哦！！！",uri+"");
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });
    }

    void Start(){
        Uri uri = RingtoneManager.getDefaultUri(1);
        mPlayer = MediaPlayer.create(this,uri);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mPlayer.start();
    }

}
