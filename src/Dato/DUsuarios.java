/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import DatabaseConnection.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import proyecto_balneario.SqlConnection;
import utils.DateString;

/**
 *
 * @author ELIAN
 */
public class DUsuarios {

    private SqlConnection connection;
    Singleton s;
    PreparedStatement ps;
    ResultSet rs;

    public DUsuarios() {
        //connection = new SqlConnection("postgres", "huanca1962", "127.0.0.1", "5432", "balnearioDB");
        s = Singleton.getInstancia();
    }

    public void guardar(String ci, String nombre, String fecha_nacimiento, String email, String password, String rol) throws SQLException, ParseException {
        String query = "INSERT INTO usuarios(ci,nombre,fecha_nacimiento,email,password,rol)"
                + "values(?,?,?,?,?,?)";

        //PreparedStatement ps = connection.connect().prepareStatement(query);
        ps = s.pgAdmin.prepareStatement(query);

        ps.setString(1, ci);
        ps.setString(2, nombre);
        ps.setDate(3, DateString.StringToDateSQL(fecha_nacimiento));
        ps.setString(4, email);
        ps.setString(5, encriptar(password));
        ps.setString(6, rol);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, String ci, String nombre, String fecha_nacimiento, String email, String password, String rol) throws SQLException, ParseException {
        String query = "UPDATE usuarios SET ci=?, nombre=?, fecha_nacimiento=?, email=?, password=?, rol=?"
                + "WHERE id=?";

        ps = connection.connect().prepareStatement(query);

        ps.setString(1, ci);
        ps.setString(2, nombre);
        ps.setDate(3, DateString.StringToDateSQL(fecha_nacimiento));
        ps.setString(4, email);
        ps.setString(5, password);
        ps.setString(6, rol);
        ps.setInt(7, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            usuarios.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nacimiento"),
                set.getString("email"),
                set.getString("password"),
                set.getString("rol")
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nacimiento"),
                set.getString("email"),
                set.getString("password"),
                set.getString("rol")
            };
        }
        return usuario;
    }

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }

    private String encriptar(String frase) {
        String letra = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        String clave = "";
        frase = frase.toUpperCase();
        for (int i = 0; i < frase.length(); i++) {
            char c = frase.charAt(i);
            int pos = letra.indexOf(c);
            if (pos == -1) {
                clave = clave + c;
            } else {
                clave = clave + letra.charAt((pos + 3) % letra.length());

            }
        }
        return clave;
    }
}
