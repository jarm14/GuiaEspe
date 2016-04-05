package com.example.espeguiada;

/**
 * Created by adrian on 28/03/16.
 */
public class SECCION {

    @com.google.gson.annotations.SerializedName("SEC_ID")
    private int SEC_ID;

    @com.google.gson.annotations.SerializedName("SEC_NOMBRE")
    private String SEC_NOMBRE;

    @com.google.gson.annotations.SerializedName("SEC_DESCRIPCION")
    private String SEC_DESCRIPCION;

    public SECCION(){}

    @Override
    public String toString() {
        return getSEC_NOMBRE();
    }

    /**
     * Initializes a new ToDoItem
     *
     * @param nombre
     *            The item nombre
     * @param id
     *            The item id
     */

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
