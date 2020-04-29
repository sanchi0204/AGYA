package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Message_and_Dial extends AppCompatActivity {

    Button msg,dial;
    String contact_name,phone;
    Intent callIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_and__dial);

        msg=findViewById(R.id.msg_contact);
        dial=findViewById(R.id.call_contact);
//
        contact_name=getIntent().getStringExtra("Contact Name");
        phone=getIntent().getStringExtra("Phone Number");

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(Message_and_Dial.this,Dial.class);
//                i.putExtra("Phone Number",phone);
//                i.putExtra("Contact Name",contact_name);
//                startActivity(i);
//                contact_name=getIntent().getStringExtra("Contact Name");
//                phone=getIntent().getStringExtra("Phone Number");
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Message_and_Dial.this,Dial.class);
                i.putExtra("Phone Number",phone);
                startActivity(i);
            }
        });
    }
}
