package com.mathome.app.entity;
import lombok.Data;

public class Nivel {
    private  int id;
    private String nivel;

    public Nivel(){

    }

    public Nivel(int id, String nivel){
        this.id = id;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return nivel;
    }
}
