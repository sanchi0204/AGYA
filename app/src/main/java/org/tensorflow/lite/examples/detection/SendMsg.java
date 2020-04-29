package org.tensorflow.lite.examples.detection;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SendMsg extends AppCompatActivity {

    String contact_name,phone,msg_text;
    Button send;
    EditText msg,phone_num;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

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

        send=findViewById(R.id.btn_send_msg);
        msg=findViewById(R.id.msg_text);
        phone_num=findViewById(R.id.msg_num);
        msg_text=msg.getText().toString();


        contact_name=getIntent().getStringExtra("Contact Name");
        phone=getIntent().getStringExtra("Phone Number");

        //Getting intent and PendingIntent instance
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        if(phone.equals(" "))
        {
            phone=phone_num.getText().toString();
        }
       else
        {
            phone_num.setText(phone);
        }
//Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg_text, pi,null);

    }


    private void speak() {
        String text;
        if(phone_num.getText().equals(" "))
        {
            text="Welcome User!Please enter a phone number to call.You may use the mic at the bottom of screen.";
        }
        else {
            if(contact_name.equals(" "))
                text = "Welcome User!Phone number entered is " + phone_num.getText() + "and the message says "+msg_text+"Do you want to continue or want to change the phone number or the message?";
            else
                text = "Welcome User!Phone number entered is " + phone_num.getText() + "and the receiver is" + contact_name + "The message says "+msg_text+"Do you want to continue or want to change the phone number or the message?";
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
