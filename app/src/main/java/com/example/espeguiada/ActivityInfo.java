package com.example.espeguiada;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.espeguiada.R;

public class ActivityInfo extends Activity {

    TextView txtInfo;
    TextView txtTitle;
    TextView txtNombreDir;
    TextView txtMailDir;
    TextView txtTelfDir;
    TextView txtMicrositio;
    String[] info;
    ImageView imgMap;
    int mapResource;
    Button btnMaps;
    View.OnClickListener btnMapsListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NextMapsActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_subseccion);

        txtTitle=(TextView) findViewById(R.id.txtTitleInfo);
        txtInfo=(TextView)findViewById(R.id.txtInfoSubseccion);
        txtNombreDir=(TextView) findViewById(R.id.txtNombreDirector);
        txtMailDir=(TextView) findViewById(R.id.txtMailDirector);
        txtTelfDir=(TextView) findViewById(R.id.txtTelfDirector);
        txtMicrositio=(TextView) findViewById(R.id.txtMicrositio);

        imgMap=(ImageView)findViewById(R.id.imageViewMap);

        Intent intent = getIntent();
        info=intent.getStringArrayExtra(MainActivity.ACT_INFO);
        //mapResource=intent.getIntExtra("Map",1);

        txtTitle.setText(info[0]);
        txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        txtInfo.setText(info[1]);
        txtInfo.setPadding(40,5,40,5);
        txtInfo.setGravity(Gravity.CENTER_HORIZONTAL);
        txtInfo.setTextSize(16);

        txtNombreDir.setText(info[3]);
        txtMailDir.setText(info[4]);
        txtTelfDir.setText(info[5]);
        txtMicrositio.setText(info[6]);

        //mapResource=0x7f020002;
        //imgMap.setImageResource(mapResource);
        //String name="logo_bio";
        String name=info[2];
        String resource="drawable";
        int res_imagen = getResources().getIdentifier(name, resource,getPackageName());
        imgMap.setImageResource(res_imagen);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        btnMaps=(Button)findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(btnMapsListener);


    }
    final static String ACT_INFO="com.example.espeguiada.MapsActivity";

    public void NextMapsActivity()
    {
        Intent maps=new Intent(this,MapsActivity.class);
        maps.putExtra(ACT_INFO,info);
        startActivity(maps);
    }

}
