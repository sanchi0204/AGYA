package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Dial extends AppCompatActivity {

    Button call;
    EditText phone_num;
    Intent callIntent;
    private TextToSpeech mTTS;
    String contact_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

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
        phone_num = findViewById(R.id.phone_number);


        call = findViewById(R.id.dial_btn);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               String con_num=phone_num.getText().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + con_num));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                    }
                    startActivity(callIntent);
                }

        });
    }

    private void speak() {
        String text;
        if(phone_num.getText().toString().isEmpty())
        {
            text="Welcome User!Please enter a phone number to call.You may use the mic at the bottom of screen.";
        }
        else {
            if(contact_name.isEmpty())
                text = "Welcome User!Phone number entered is " + phone_num.getText() + "Do you want to continue or want to change the phone number?";
            else
                 text = "Welcome User!Phone number entered is " + phone_num.getText() + "and the receiver is" + contact_name + "Do you want to continue or want to change the phone number?";
        }

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
