package com.example.espeguiada;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by diego on 14/07/2016.
 */
public class Ubicacion implements LocationListener {
    private Context ctx;
    LocationManager locationMan;
    private Location lc;
    private String proveedor;
    private boolean networkon;
    private double latitud;
    private double longitud;


    public Ubicacion(Context ctx) {
        this.ctx = ctx;
        locationMan = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.GPS_PROVIDER;
        networkon = locationMan.isProviderEnabled(proveedor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ctx.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ctx.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }else
            {
                locationMan.requestLocationUpdates(proveedor, 1000, 1, this);
            }
        } else {
            locationMan.requestLocationUpdates(proveedor, 1000, 1, this);
        }
        getLocation();
    }

    public void getLocation()
    {
        if(networkon)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ctx.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ctx.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return ;
                }
            }
            /*if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( ctx, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }*/
            lc=locationMan.getLastKnownLocation(proveedor);
            if(lc!=null)
            {
                latitud=lc.getLatitude();
                longitud=lc.getLongitude();
                StringBuilder sbuilder= new StringBuilder();
                sbuilder.append("Latitud: ").append(lc.getLatitude()).append(" Longitud:").append(lc.getLongitude());
                //Toast.makeText(ctx, sbuilder.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        getLocation();

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
