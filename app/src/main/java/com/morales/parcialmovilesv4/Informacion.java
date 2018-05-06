package com.morales.parcialmovilesv4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Karla on 29/04/2018.
 */

public class Informacion implements Serializable{
    private String ID, nombre, numero, direccion;
    private int img;
    private boolean fav;

    public Informacion() {
    }

    public Informacion(String ID, String nombre, String numero, String direccion, int img, boolean fav) {
        this.ID = ID;
        this.nombre = nombre;
        this.numero = numero;
        this.direccion = direccion;
        this.img = img;
        this.fav = fav;
    }

    public Informacion(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
    public Boolean getFav() {
        return fav;
    }

}
