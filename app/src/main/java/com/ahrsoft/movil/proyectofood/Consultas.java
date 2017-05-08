package com.ahrsoft.movil.proyectofood;

/**
 * Created by geekahr on 5/4/17.
 */

public class Consultas {
    private String title;
    private String desc;
    private String imgbanner;
    private String imglogo;
    private String status;
    private String domicilio;
    private String Horario;
    private String latitud;
    private String longitud;
    private String uid;

    public Consultas(){

    }



    public Consultas(String title, String desc, String imgbanner, String imglogo, String status, String domicilio, String horario, String latitud, String longitud, String uid) {
        this.title = title;
        this.desc = desc;
        this.imgbanner = imgbanner;
        this.imglogo = imglogo;
        this.status = status;
        this.Horario = horario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.uid = uid;
        this.domicilio = domicilio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgbanner() {
        return imgbanner;
    }

    public void setImgbanner(String imgbanner) {
        this.imgbanner = imgbanner;
    }

    public String getImglogo() {
        return imglogo;
    }

    public void setImglogo(String imglogo) {
        this.imglogo = imglogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

