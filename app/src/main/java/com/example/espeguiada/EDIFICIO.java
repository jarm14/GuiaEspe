package com.example.espeguiada;

/**
 * Created by adrian on 28/03/16.
 */
public class EDIFICIO {

    @com.google.gson.annotations.SerializedName("id")
    private int id;
    @com.google.gson.annotations.SerializedName("EDI_NOMBRE")
    private String EDI_NOMBRE;
    @com.google.gson.annotations.SerializedName("EDI_UBICACION")
    private String EDI_UBICACION;
    @com.google.gson.annotations.SerializedName("deleted")
    private boolean deleted;


    public EDIFICIO(){}

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getEDI_NOMBRE() {
        return EDI_NOMBRE;
    }

    public void setEDI_NOMBRE(String EDI_NOMBRE) {
        this.EDI_NOMBRE = EDI_NOMBRE;
    }

    public String getEDI_UBICACION() {
        return EDI_UBICACION;
    }

    public void setEDI_UBICACION(String EDI_UBICACION) {
        this.EDI_UBICACION = EDI_UBICACION;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
