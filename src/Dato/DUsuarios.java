/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import DatabaseConnection.SqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import utils.DateString;

/**
 *
 * @author ELIAN
 */
public class DUsuarios {

    public static final String[] HEADERS = 
        {"ID","CI","NOMBRE","FECHA NACIMIENTO", "EMAIL", "ROL"};
    
    private final SqlConnection connection;

    public DUsuarios() {
        connection = new SqlConnection();
    }

    public void guardar(String ci, String nombre, String fecha_nacimiento, String email, String password, String rol) throws SQLException, ParseException {
        String query = "INSERT INTO usuarios(ci,nombre,fecha_nacimiento,email,password,rol)"
                + "values(?,?,?,?,?,?)";
                //+ "values(?,?,?,?,crypt(?, gen_salt('bf')),?)";
                    
        PreparedStatement ps = connection.connect().prepareStatement(query);        

        ps.setString(1, ci);
        ps.setString(2, nombre);
        ps.setDate(3, DateString.StringToDateSQL(fecha_nacimiento));
        ps.setString(4, email);
        ps.setString(5, password);
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

        PreparedStatement ps = connection.connect().prepareStatement(query);        

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
                set.getString("rol")
            };
        }
        return usuario;
    }

    public int getIdByEmail(String email) throws SQLException {
        int id = -1;
        String query = "SELECT * FROM usuarios WHERE email=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, email);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            id = set.getInt("id");
        }

        return id;
    }

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }

}
