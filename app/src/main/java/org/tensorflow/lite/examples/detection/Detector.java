package org.tensorflow.lite.examples.detection;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Detector extends AppCompatActivity {

    ImageView img;
    Button detect;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        img=findViewById(R.id.img);
        text=findViewById(R.id.detect_text);
        detect=findViewById(R.id.detect_btn);


    }
}
