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
 * @author Elian
 */
public class DReservas {

    private final SqlConnection connection;

    public DReservas() {
        connection = new SqlConnection();
    }

    public void guardar(String fecha, String turno, int idUsuario) throws SQLException, ParseException {
                String query = "INSERT INTO reservas(fecha,turno,idUsuario)"
                + "values(?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setDate(1, DateString.StringToDateSQL(fecha));
        ps.setString(2, turno);
        ps.setInt(3, idUsuario);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DReservas.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, String fecha, String turno, int idUsuario) throws SQLException, ParseException {
        String query = "UPDATE reservas SET fecha=?, turno=?, idUsuario=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setDate(1, DateString.StringToDateSQL(fecha));
        ps.setString(2, turno);
        ps.setInt(3, idUsuario);
        ps.setInt(4, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DReservas.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM reservas WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DReservas.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM reservas";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            usuarios.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("turno"),                
                String.valueOf(set.getInt("idUsuario")),
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM reservas WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("turno"),                
                String.valueOf(set.getInt("idUsuario")),
            };
        }
        return usuario;
    }

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }
}
