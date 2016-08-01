package com.example.espeguiada;

/**
 * Created by adr95 on 16/06/2016.
 */
public class DIRECTORDEP {
    private String id;
    private String SUB_ID;
    private String DIR_NOMBRE;
    private String DIR_APELLIDO;
    private String DIR_MAIL;
    private String DIR_TELEFONO;
    private String DIR_ABTITULO;

    public DIRECTORDEP(){}

    public DIRECTORDEP(String id, String SUB_ID, String DIR_NOMBRE, String DIR_APELLIDO, String DIR_MAIL, String DIR_TELEFONO, String DIR_ABTITULO){
        this.setId(id);
        this.setSUB_ID(SUB_ID);
        this.setDIR_NOMBRE(DIR_NOMBRE);
        this.setDIR_APELLIDO(DIR_APELLIDO);
        this.setDIR_MAIL(DIR_MAIL);
        this.setDIR_TELEFONO(DIR_TELEFONO);
        this.setDIR_ABTITULO(DIR_ABTITULO);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSUB_ID() {
        return SUB_ID;
    }

    public void setSUB_ID(String SUB_ID) {
        this.SUB_ID = SUB_ID;
    }

    public String getDIR_NOMBRE() {
        return DIR_NOMBRE;
    }

    public void setDIR_NOMBRE(String DIR_NOMBRE) {
        this.DIR_NOMBRE = DIR_NOMBRE;
    }

    public String getDIR_APELLIDO() {
        return DIR_APELLIDO;
    }

    public void setDIR_APELLIDO(String DIR_APELLIDO) {
        this.DIR_APELLIDO = DIR_APELLIDO;
    }

    public String getDIR_MAIL() {
        return DIR_MAIL;
    }

    public void setDIR_MAIL(String DIR_MAIL) {
        this.DIR_MAIL = DIR_MAIL;
    }

    public String getDIR_TELEFONO() {
        return DIR_TELEFONO;
    }

    public void setDIR_TELEFONO(String DIR_TELEFONO) {
        this.DIR_TELEFONO = DIR_TELEFONO;
    }

    public String getDIR_ABTITULO() {
        return DIR_ABTITULO;
    }

    public void setDIR_ABTITULO(String DIR_ABTITULO) {
        this.DIR_ABTITULO = DIR_ABTITULO;
    }
}
