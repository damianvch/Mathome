package com.mathome.app.entity;
import lombok.Data;

public class Nivel {
    private  int id;
    private String nivel;
    private String token;

    public Nivel(){

    }

    public Nivel(int id, String nivel, String token){
        this.id = id;
        this.nivel = nivel;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return nivel;
    }
}
