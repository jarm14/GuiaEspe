package com.example.espeguiada;

import android.graphics.Bitmap;

/**
 * Created by adrian on 28/03/16.
 */
public class SUBSECCION {

    private int SUB_ID;
    private int SEC_ID;
    private int EDI_ID;
    private String SUB_NOMBRE;
    private String SUB_DESCRIPCION;
    private String SUB_UBICACION;
    private Bitmap SUB_LOGO;

    public SUBSECCION(){}


    public int getSUB_ID() {
        return SUB_ID;
    }

    public void setSUB_ID(int SUB_ID) {
        this.SUB_ID = SUB_ID;
    }

    public int getSEC_ID() {
        return SEC_ID;
    }

    public void setSEC_ID(int SEC_ID) {
        this.SEC_ID = SEC_ID;
    }

    public int getEDI_ID() {
        return EDI_ID;
    }

    public void setEDI_ID(int EDI_ID) {
        this.EDI_ID = EDI_ID;
    }

    public String getSUB_NOMBRE() {
        return SUB_NOMBRE;
    }

    public void setSUB_NOMBRE(String SUB_NOMBRE) {
        this.SUB_NOMBRE = SUB_NOMBRE;
    }

    public String getSUB_DESCRIPCION() {
        return SUB_DESCRIPCION;
    }

    public void setSUB_DESCRIPCION(String SUB_DESCRIPCION) {
        this.SUB_DESCRIPCION = SUB_DESCRIPCION;
    }

    public String getSUB_UBICACION() {
        return SUB_UBICACION;
    }

    public void setSUB_UBICACION(String SUB_UBICACION) {
        this.SUB_UBICACION = SUB_UBICACION;
    }

    public Bitmap getSUB_LOGO() {
        return SUB_LOGO;
    }

    public void setSUB_LOGO(Bitmap SUB_LOGO) {
        this.SUB_LOGO = SUB_LOGO;
    }
}
