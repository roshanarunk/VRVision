package org.syriancarrot.hellovr;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    public static float fov;
    public static float brightness;
    public static float size;
    public static float pos_x;
    public static float pos_y;
    public static float off_x;
    public static float off_y;
    public static float dif_x;
    public static float dif_y;

    private EditText fovedit;
    private EditText brightedit;
    private EditText sizeEdit;
    private EditText pos_xEdit;
    private EditText pos_yEdit;
    private EditText off_xEdit;
    private EditText off_yEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fovedit = (EditText)findViewById(R.id.fov);
        brightedit = (EditText)findViewById(R.id.brightness);

        sizeEdit  = (EditText)findViewById(R.id.size);
        pos_xEdit = (EditText)findViewById(R.id.pos_x);
        pos_yEdit = (EditText)findViewById(R.id.pos_y);
        off_xEdit = (EditText)findViewById(R.id.off_x);
        off_yEdit = (EditText)findViewById(R.id.off_y);

//        Button start = (Button)findViewById(R.id.start);
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                fov = Float.parseFloat(fovedit.getText().toString());
//                fov = fov/100;
//
//                brightness = Float.parseFloat(brightedit.getText().toString());
//                if (brightness < 0.1f)
//                    brightness = 0.1f;
//                if (brightness > 2.0f)
//                    brightness = 2.0f;
//
//                size = Float.parseFloat(sizeEdit.getText().toString());
//                pos_x = Float.parseFloat(pos_xEdit.getText().toString());
//                pos_y = Float.parseFloat(pos_yEdit.getText().toString());
//                off_x = Float.parseFloat(off_xEdit.getText().toString());
//                off_y = Float.parseFloat(off_yEdit.getText().toString());
//
//                dif_x = off_x - pos_x;
//                dif_y = off_y - pos_y;
//
//                startActivity(new Intent(MainActivity.this, SimpleVrPanoramaActivity.class));
//
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fov = Float.parseFloat(fovedit.getText().toString());
                fov = fov/100;

                brightness = Float.parseFloat(brightedit.getText().toString());
                if (brightness < 0.1f)
                    brightness = 0.1f;
                if (brightness > 2.0f)
                    brightness = 2.0f;

                size = Float.parseFloat(sizeEdit.getText().toString());
                pos_x = Float.parseFloat(pos_xEdit.getText().toString());
                pos_y = Float.parseFloat(pos_yEdit.getText().toString());
                off_x = Float.parseFloat(off_xEdit.getText().toString());
                off_y = Float.parseFloat(off_yEdit.getText().toString());

                dif_x = off_x - pos_x;
                dif_y = off_y - pos_y;

                startActivity(new Intent(MainActivity.this, SimpleVrPanoramaActivity.class));

            }
        });


    }



}
