package com.example.espeguiada;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.espeguiada.R;

public class ActivityInfo extends Activity {

    TextView txtInfo;
    String[] info;
    ImageView imgMap;
    int mapResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_subseccion);


        txtInfo=(TextView)findViewById(R.id.txtInfoSubseccion);
        imgMap=(ImageView)findViewById(R.id.imageViewMap);

        Intent intent = getIntent();
        info=intent.getStringArrayExtra(MainActivity.ACT_INFO);
        mapResource=intent.getIntExtra("Map",1);

        txtInfo.setText(info[1]);
        imgMap.setImageResource(mapResource);

        //getActionBar().setDisplayHomeAsUpEnabled(true);



    }

}
