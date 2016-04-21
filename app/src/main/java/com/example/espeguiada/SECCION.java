package com.example.espeguiada;

/**
 * Created by adrian on 28/03/16.
 */
public class SECCION {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("SEC_NOMBRE")
    private String SEC_NOMBRE;

    @com.google.gson.annotations.SerializedName("SEC_DESCRIPCION")
    private String SEC_DESCRIPCION;

    @com.google.gson.annotations.SerializedName("deleted")
    private boolean deleted;

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

    public SECCION(String id, String nombre, String descripcion) {
        this.id=id;
        this.SEC_NOMBRE=nombre;
        this.SEC_DESCRIPCION=descripcion;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
