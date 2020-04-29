package org.tensorflow.lite.examples.detection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Battery extends AppCompatActivity {

    private TextToSpeech mTTS;
    TextView battery,battery_status;
    String status;
    String battery_level;
    int level;
    boolean isPlugged;
    private BroadcastReceiver BatteryReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
           status= BatteryManager.EXTRA_STATUS;
            battery_level= level +"Z%";
            battery.setText(battery_level);



            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            isPlugged = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
            isPlugged = isPlugged || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;

            if(isPlugged)
            battery_status.setText("Charging");
             else
                battery_status.setText("Not Charging");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        speak();
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        battery=findViewById(R.id.battery_percentage);
        battery_status=findViewById(R.id.battery_state);

        this.registerReceiver(this.BatteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private void speak() {
        String battery_state;
        if(isPlugged)
            battery_state="Charging";
        else
            battery_state="Not Charging";

        String text="Battery percentage is "+level+" and phone is"+ battery_state;

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }
}
