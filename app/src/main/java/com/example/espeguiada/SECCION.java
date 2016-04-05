package com.example.espeguiada;

/**
 * Created by adrian on 28/03/16.
 */
public class SECCION {

    private int SEC_ID;
    private String SEC_NOMBRE;
    private String SEC_DESCRIPCION;

    public SECCION(){}

    public SECCION(int id, String nombre, String descripcion) {
        this.SEC_ID=id;
        this.SEC_NOMBRE=nombre;
        this.SEC_DESCRIPCION=descripcion;

    }

    public int getSEC_ID() {
        return SEC_ID;
    }

    public void setSEC_ID(int SEC_ID) {
        this.SEC_ID = SEC_ID;
    }

    public String getSEC_NOMBRE() {
        return SEC_NOMBRE;
    }

    public void setSEC_NOMBRE(String SEC_NOMBRE) {
        this.SEC_NOMBRE = SEC_NOMBRE;
    }

    public String getSEC_DESCRIPCION() {
        return SEC_DESCRIPCION;
    }

    public void setSEC_DESCRIPCION(String SEC_DESCRIPCION) {
        this.SEC_DESCRIPCION = SEC_DESCRIPCION;
    }
}
