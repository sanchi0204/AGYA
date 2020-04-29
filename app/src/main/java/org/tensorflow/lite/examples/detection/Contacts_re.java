package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.Adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class Contacts_re extends AppCompatActivity {
    ArrayList<String> StoreContacts ;
    ArrayList<String> StoreNumbers ;
    RecyclerView recycler_contacts;
    Cursor cursor ;
    String name, phonenumber ;
    private TextToSpeech mTTS;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_re);

        StoreContacts = new ArrayList<>();
        StoreNumbers = new ArrayList<>();
        EnableRuntimePermission();

        recycler_contacts=findViewById(R.id.recycler_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_contacts.setLayoutManager(linearLayoutManager);

        GetContactsIntoArrayList();

        ContactAdapter contactAdapter = new ContactAdapter(StoreContacts,StoreNumbers, this);
        recycler_contacts.setAdapter(contactAdapter);

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
    }

    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        assert cursor != null;
        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name);
            StoreNumbers.add(phonenumber);
        }

        cursor.close();

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                Contacts_re.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(Contacts_re.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Contacts_re.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, @NonNull String[] per, @NonNull int[] PResult) {

        if (RC == RequestPermissionCode) {
            if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(Contacts_re.this, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(Contacts_re.this, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void speak() {
        String text="Welcome User!This is the contact list. Please Click on the contact you want to call or message.";

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
