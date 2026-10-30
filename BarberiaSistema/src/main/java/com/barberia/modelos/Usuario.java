
package com.barberia.modelos;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;

    public Usuario(int id, String nombre, String correo,
                   String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }
}
