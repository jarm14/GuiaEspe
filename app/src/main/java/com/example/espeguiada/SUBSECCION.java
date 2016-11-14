package com.example.espeguiada;

/**
 * Created by adrian on 28/03/16.
 */
public class SUBSECCION {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    @com.google.gson.annotations.SerializedName("SEC_ID")
    private String SEC_ID;
    @com.google.gson.annotations.SerializedName("EDI_ID")
    private String EDI_ID;
    @com.google.gson.annotations.SerializedName("SUB_NOMBRE")
    private String SUB_NOMBRE;
    @com.google.gson.annotations.SerializedName("SUB_DESCRIPCION")
    private String SUB_DESCRIPCION;
    @com.google.gson.annotations.SerializedName("SUB_UBICACION")
    private String SUB_UBICACION;
    private String SUB_INSTRUCCIONES;
    //@com.google.gson.annotations.SerializedName("SUB_LOGO")
    //private Bitmap SUB_LOGO;
    @com.google.gson.annotations.SerializedName("deleted")

    private String SUB_LOGO;
    private String SUB_MICROSITIO;

    public SUBSECCION(){}

    public SUBSECCION(String id, String SEC_ID, String EDI_ID, String SUB_NOMBRE, String SUB_DESCRIPCION, String SUB_UBICACION,String SUB_INSTRUCCIONES, String SUB_LOGO, String SUB_MICROSITIO) {
        this.setId(id);
        this.setSEC_ID(SEC_ID);
        this.setEDI_ID(EDI_ID);
        this.SUB_NOMBRE = SUB_NOMBRE;
        this.SUB_DESCRIPCION = SUB_DESCRIPCION;
        this.SUB_UBICACION = SUB_UBICACION;
        this.SUB_LOGO=SUB_LOGO;
        this.setSUB_MICROSITIO(SUB_MICROSITIO);
        this.SUB_INSTRUCCIONES=SUB_INSTRUCCIONES;

        //this.SUB_LOGO = SUB_LOGO;
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

    /*public Bitmap getSUB_LOGO() {
        return SUB_LOGO;
    }

    public void setSUB_LOGO(Bitmap SUB_LOGO) {
        this.SUB_LOGO = SUB_LOGO;
    }*/




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSEC_ID() {
        return SEC_ID;
    }

    public void setSEC_ID(String SEC_ID) {
        this.SEC_ID = SEC_ID;
    }

    public String getEDI_ID() {
        return EDI_ID;
    }

    public void setEDI_ID(String EDI_ID) {
        this.EDI_ID = EDI_ID;
    }

    public String getSUB_LOGO() {
        return SUB_LOGO;
    }

    public void setSUB_LOGO(String SUB_LOGO) {
        this.SUB_LOGO = SUB_LOGO;
    }

    public String getSUB_MICROSITIO() {
        return SUB_MICROSITIO;
    }

    public void setSUB_MICROSITIO(String SUB_MICROSITIO) {
        this.SUB_MICROSITIO = SUB_MICROSITIO;
    }

    public String getSUB_INSTRUCCIONES() {
        return SUB_INSTRUCCIONES;
    }

    public void setSUB_INSTRUCCIONES(String SUB_INSTRUCCIONES) {
        this.SUB_INSTRUCCIONES = SUB_INSTRUCCIONES;
    }
}
