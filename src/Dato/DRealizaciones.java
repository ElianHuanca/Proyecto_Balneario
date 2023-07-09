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
public class DRealizaciones {
    private final SqlConnection connection;

    public DRealizaciones() {
        connection = new SqlConnection();
    }

    public void guardar(int idMantenimiento, int idAmbiente, String fecha) throws SQLException, ParseException {
                String query = "INSERT INTO realizaciones(idMantenimiento,idAmbiente,fecha)"
                + "values(?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);        
        ps.setInt(1, idMantenimiento);
        ps.setInt(2, idAmbiente);
        ps.setDate(3, DateString.StringToDateSQL(fecha));

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Drealizaciones.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, int idMantenimiento, int idAmbiente, String fecha) throws SQLException, ParseException {
        String query = "UPDATE realizaciones SET idMantenimiento=?, idAmbiente=?, fecha=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idMantenimiento);
        ps.setInt(2, idAmbiente);
        ps.setDate(3, DateString.StringToDateSQL(fecha));
        ps.setInt(4, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Drealizaciones.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM realizaciones WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class Drealizaciones.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM realizaciones";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            usuarios.add(new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getInt("idMantenimiento")),
                String.valueOf(set.getInt("idAmbiente")),
                set.getString("fecha"),                                                
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM realizaciones WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getInt("idMantenimiento")),
                String.valueOf(set.getInt("idAmbiente")),
                set.getString("fecha"),                                                
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
