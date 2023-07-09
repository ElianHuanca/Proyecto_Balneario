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
public class DUsos {

    private final SqlConnection connection;

    public DUsos() {
        connection = new SqlConnection();
    }

    public void guardar(int idMantenimiento, int idProducto) throws SQLException, ParseException {
        String query = "INSERT INTO usos(idMantenimiento,idProducto)"
                + "values(?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idMantenimiento);
        ps.setInt(2, idProducto);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Dusos.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, int idMantenimiento, int idProducto) throws SQLException, ParseException {
        String query = "UPDATE usos SET idMantenimiento=?, idProducto=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idMantenimiento);
        ps.setInt(2, idProducto);
        ps.setInt(3, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Dusos.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM usos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Dusos.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usos";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            usuarios.add(new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getInt("idMantenimiento")),
                String.valueOf(set.getInt("idProducto")),
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM usos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getInt("idMantenimiento")),
                String.valueOf(set.getInt("idProducto")),
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