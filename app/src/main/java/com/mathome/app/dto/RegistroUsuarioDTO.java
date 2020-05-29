package com.mathome.app.dto;
import java.io.Serializable;

public class RegistroUsuarioDTO implements Serializable {
    private String _nombre;
    private String _apellido;

    public RegistroUsuarioDTO() {
    }

    public RegistroUsuarioDTO(String _nombre, String _apellido) {
        this._nombre = _nombre;
        this._apellido = _apellido;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_apellido() {
        return _apellido;
    }

    public void set_apellido(String _apellido) {
        this._apellido = _apellido;
    }
}
