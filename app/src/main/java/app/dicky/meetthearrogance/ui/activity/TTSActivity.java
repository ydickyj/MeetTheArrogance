package app.dicky.meetthearrogance.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import app.dicky.meetthearrogance.R;

public class TTSActivity extends Fragment {
    /** Called when the activity is first created. */
    TextToSpeech tts;
	Button btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tts, container, false);
        btn = (Button) view.findViewById(R.id.btn_speak);
        final Context newC = this.getActivity().getApplication().getBaseContext();
        tts = new TextToSpeech(newC, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {/**如果装载TTS成功*/
                    int result = tts.setLanguage(Locale.CHINESE);/**有Locale.CHINESE,但是不支持中文*/
                    if (result == TextToSpeech.LANG_MISSING_DATA/**表示语言的数据丢失。*/
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {/**语言不支持*/
                        Toast.makeText(newC, "我说不出口", Toast.LENGTH_SHORT).show();
                    } else {
                        tts.setSpeechRate(1.5f);
                        tts.speak("TTS初始化成功", TextToSpeech.QUEUE_FLUSH,
                                null);
                    }
                }
            }
        });
        //实例化
        btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tts.speak("莹莹，在人流中，我一眼就发现了你。我不敢说你是她们中最漂亮的一个，可是我敢说，你是她们中最出色的一个。", TextToSpeech.QUEUE_FLUSH, null);
                //语音输出
            }
        });
        return view;
    }



    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}