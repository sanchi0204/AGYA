package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Dashbboard extends AppCompatActivity {

    private TextToSpeech mTTS;
    TextView battery,contacts,messages,dial,surroundings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbboard);

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
        battery=findViewById(R.id.battery_msg);
        contacts=findViewById(R.id.contacts);
        dial=findViewById(R.id.dial_number);
        messages=findViewById(R.id.message);
        surroundings=findViewById(R.id.surroundings);

        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashbboard.this,Battery.class);
                startActivity(i);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashbboard.this,Contacts_re.class);
                startActivity(i);
            }
        });

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashbboard.this,Dial.class);
                startActivity(i);
            }
        });

        surroundings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashbboard.this,DetectorActivity.class);
                startActivity(i);
            }
        });
//
//        messages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO
//            }
//        });
    }

    private void speak() {
        String text="Welcome User! This is the Dashboard.You can use it to select contacts, make calls, send or receive messages and " +
                "check your battery condition";

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
