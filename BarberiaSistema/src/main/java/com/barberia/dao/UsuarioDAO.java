
package com.barberia.dao;

import com.barberia.config.ConexionBD;
import com.barberia.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public boolean registrar(String nombre, String correo, String clave)
            throws SQLException {

        String sql = "INSERT INTO usuarios (nombre, correo, password, rol) "
                   + "VALUES (?, ?, ?, 'CLIENTE')";

        String hash = BCrypt.hashpw(clave, BCrypt.gensalt(12));

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, hash);

            return ps.executeUpdate() == 1;
        }
    }

    public Usuario iniciarSesion(String correo, String clave)
            throws SQLException {

        String sql = "SELECT id, nombre, correo, password, rol "
                   + "FROM usuarios "
                   + "WHERE correo = ? AND activo = TRUE";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password");

                    if (BCrypt.checkpw(clave, hash)) {
                        return new Usuario(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                hash,
                                rs.getString("rol")
                        );
                    }
                }
            }
        }

        return null;
    }
}
