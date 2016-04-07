package com.example.espeguiada;

import android.graphics.Bitmap;

/**
 * Created by adrian on 28/03/16.
 */
public class SUBSECCION {

    @com.google.gson.annotations.SerializedName("id")
    private int id;
    @com.google.gson.annotations.SerializedName("SEC_ID")
    private int SEC_ID;
    @com.google.gson.annotations.SerializedName("EDI_ID")
    private int EDI_ID;
    @com.google.gson.annotations.SerializedName("SUB_NOMBRE")
    private String SUB_NOMBRE;
    @com.google.gson.annotations.SerializedName("SUB_DESCRIPCION")
    private String SUB_DESCRIPCION;
    @com.google.gson.annotations.SerializedName("SUB_UBICACION")
    private String SUB_UBICACION;
    @com.google.gson.annotations.SerializedName("SUB_LOGO")
    private Bitmap SUB_LOGO;
    @com.google.gson.annotations.SerializedName("deleted")
    private boolean deleted;

    public SUBSECCION(){}


    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
