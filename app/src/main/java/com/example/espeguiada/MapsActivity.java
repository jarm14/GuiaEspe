package com.example.espeguiada;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Ubicacion ub;
    private String[] info;
    private double lat;
    private double len;
    private List<String> puntos;
    private PolylineOptions polyop;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ub = new Ubicacion(this);
        Intent intent=getIntent();
        info=intent.getStringArrayExtra(ActivityInfo.ACT_INFO);

        final FloatingActionButton refresh=(FloatingActionButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polyline.remove();

                generarRuta(new LatLng(ub.getLatitud(),ub.getLongitud()),new LatLng(lat,len));
                Interpolator inter= new OvershootInterpolator();
                //AnimationUtils.loadInterpolator(getApplicationContext(),android.R.interpolator.fast_out_slow_in);
                ViewCompat.animate(refresh).rotation(360f).setDuration(1000).setInterpolator(inter).start();
                Snackbar snack=Snackbar.make(view,"Ruta generada nuevamente",Snackbar.LENGTH_LONG);
                View snackView=snack.getView();
                snackView.setBackgroundColor(Color.BLACK);
                TextView textSnack=(TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
                textSnack.setTextColor(Color.WHITE);
                snack.setDuration(1500).show();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            KmlLayer layerESPE=new KmlLayer(mMap,R.raw.espekml2,getApplicationContext());
            layerESPE.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inicializarLista();
        coordenadas();
        LatLng decc = new LatLng(lat, len);
        mMap.addMarker(new MarkerOptions().position(decc).title(info[0]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(decc));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setTiltGesturesEnabled(true);

        }else
        {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setTiltGesturesEnabled(true);

        }
        generarRuta(new LatLng(ub.getLatitud(),ub.getLongitud()),new LatLng(lat,len));
    }

    private void coordenadas()
    {
        String aux=info[7];
        String[] latlen;
        latlen=aux.split(",");
        lat=Double.parseDouble(latlen[0]);
        len=Double.parseDouble(latlen[1]);
    }

    private void generarRuta(LatLng ini, LatLng fin)
    {
        polyop=new PolylineOptions();
        LatLng inicio,aux1,aux2 = null,aux3 = null;
        double disT=0,disP=0,latP=0,lonP=0,disAux=0,disMin=0,disAuxT=0;
        int index=0;
        String[] latleng;
        inicio=ini;//new LatLng(-0.312775, -78.445559);
        //fin=new LatLng(-0.31387,-78.44494);
        disT= SphericalUtil.computeDistanceBetween(inicio,fin);
        aux1=inicio;
        disP=disT;
        polyop.add(inicio);
        do {
            disMin=disP;
            for(int i=0;i<puntos.size();i++)
            {
                latleng=puntos.get(i).split(",");
                latP=Double.parseDouble(latleng[0]);
                lonP=Double.parseDouble(latleng[1]);
                aux2=new LatLng(latP,lonP);
                disAux=SphericalUtil.computeDistanceBetween(aux1,aux2);
                disAuxT=SphericalUtil.computeDistanceBetween(aux2,fin);
                if(disAux<=disMin &&  disAux<disT && disAux!=0 && disAuxT<disP)
                {
                    disMin=disAux;
                    index=i;
                }
                if(aux1==fin)
                {
                    index=i;
                }

            }
            latleng=puntos.get(index).split(",");
            latP=Double.parseDouble(latleng[0]);
            lonP=Double.parseDouble(latleng[1]);
            aux3=new LatLng(latP,lonP);
            polyop.add(aux3);
            aux1=aux3;
            disP=SphericalUtil.computeDistanceBetween(aux1,fin);
        }while(disP!=0.0);
        polyop.add(fin);
        dibujarRuta(polyop);
    }

    private void dibujarRuta(PolylineOptions options){
        polyline = mMap.addPolyline(options);
        polyline.setWidth(5);
        polyline.setColor(Color.BLUE);
        polyline.setGeodesic(true);
    }

    private void inicializarLista()
    {
        puntos=new ArrayList<String>();
        puntos.add("-0.31509,-78.44228");
        puntos.add("-0.315,-78.44296");
        puntos.add("-0.3149,-78.44314");
        puntos.add("-0.31435,-78.44292");
        puntos.add("-0.31432,-78.4431");
        puntos.add("-0.31429,-78.44346");
        puntos.add("-0.31398,-78.44288");
        puntos.add("-0.3136,-78.44301");
        puntos.add("-0.31336,-78.4432");
        puntos.add("-0.31322,-78.44323");
        puntos.add("-0.31398,-78.44345");
        puntos.add("-0.31398,-78.4436");
        puntos.add("-0.31384,-78.44358");
        puntos.add("-0.31411,-78.44361");
        puntos.add("-0.31427,-78.44391");
        puntos.add("-0.31424,-78.44425");
        puntos.add("-0.31354,-78.44402");
        puntos.add("-0.31356,-78.4442");
        puntos.add("-0.31322,-78.44417");
        puntos.add("-0.31319,-78.44449");
        puntos.add("-0.31317,-78.44486");
        puntos.add("-0.31338,-78.44489");
        puntos.add("-0.31346,-78.44489");
        puntos.add("-0.31328,-78.44504");
        puntos.add("-0.31327,-78.44529");
        puntos.add("-0.31291,-78.44549");
        puntos.add("-0.31278,-78.44548");
        puntos.add("-0.31286,-78.44482");
        puntos.add("-0.31269,-78.44523");
        puntos.add("-0.31218,-78.44517");
        puntos.add("-0.31186,-78.44577");
        puntos.add("-0.3124,-78.44581");
        puntos.add("-0.31267,-78.44556");
        puntos.add("-0.31276,-78.44584");
        puntos.add("-0.31291,-78.44591");
        puntos.add("-0.31373,-78.44598");
        puntos.add("-0.3137,-78.4464");
        puntos.add("-0.31337,-78.44636");
        puntos.add("-0.31461,-78.44606");
        puntos.add("-0.3146,-78.44667");
        puntos.add("-0.31463,-78.44584");
        puntos.add("-0.31468,-78.44578");
        puntos.add("-0.3147,-78.44558");
        puntos.add("-0.31418,-78.44554");
        puntos.add("-0.31385,-78.44534");
        puntos.add("-0.31387,-78.44494");
        puntos.add("-0.31415,-78.44496");
        puntos.add("-0.31486,-78.44559");
        puntos.add("-0.31489,-78.44549");
        puntos.add("-0.31489,-78.44549");
        puntos.add("-0.31509,-78.44557");
        puntos.add("-0.3148,-78.44437");
        puntos.add("-0.31485,-78.44368");
        puntos.add("-0.31567,-78.4433");
        puntos.add("-0.31604,-78.44347");
        puntos.add("-0.31568,-78.44376");
        puntos.add("-0.31545,-78.44385");
        puntos.add("-0.31536,-78.44394");
        puntos.add("-0.31657,-78.44308");
        puntos.add("-0.31678,-78.4431");
        puntos.add("-0.31678,-78.44383");
        puntos.add("-0.31704,-78.44419");
        puntos.add("-0.3172,-78.44412");
        puntos.add("-0.31746,-78.44412");
        puntos.add("-0.31739,-78.44447");
        puntos.add("-0.31751,-78.44461");
        puntos.add("-0.31771,-78.44472");
        puntos.add("-0.31866,-78.44493");
        puntos.add("-0.31894,-78.44516");
        puntos.add("-0.3191,-78.44555");
        puntos.add("-0.314622, -78.443726");

        puntos.add("-0.314060, -78.445605");
        puntos.add("-0.313006, -78.445524");
        puntos.add("-0.314060, -78.445605");
        puntos.add("-0.314060, -78.445605");
        puntos.add("-0.314052, -78.445317");
        puntos.add("-0.314052, -78.445317");
        puntos.add("-0.314060, -78.445605");
        puntos.add("-0.313976, -78.443638");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314052, -78.445317");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314060, -78.445605");
        puntos.add("-0.312920, -78.445329");
        puntos.add("-0.315092, -78.445480");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.313848, -78.445512");
        puntos.add("-0.314052, -78.445317");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.312920, -78.445329");
        puntos.add("-0.314289, -78.445392");
        puntos.add("-0.313348, -78.446412");
        puntos.add("-0.314622, -78.443726");
        puntos.add("-0.312920, -78.445329");

    }

}
