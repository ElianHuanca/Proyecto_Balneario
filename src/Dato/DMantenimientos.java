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

/**
 *
 * @author Elian
 */
public class DMantenimientos {
    private final SqlConnection connection;

    public DMantenimientos() {
        connection = new SqlConnection();
    }

    public void guardar(String nombre,  float montoTotal ) throws SQLException, ParseException {
        String query = "INSERT INTO mantenimientos(nombre,montoTotal)"
                + "values(?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);        
        ps.setString(1, nombre);        
        ps.setFloat(2, montoTotal);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMantenimientos.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id,String nombre,  float montoTotal) throws SQLException, ParseException {
        String query = "UPDATE mantenimientos SET nombre=?, descripcion=?, precio=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);        
        ps.setFloat(2, montoTotal);      
        ps.setInt(3,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMantenimientos.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM mantenimientos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMantenimientos.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM mantenimientos";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),                
                String.valueOf(set.getFloat("montoTotal")),
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM mantenimientos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            usuario = new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),                
                String.valueOf(set.getFloat("montoTotal")),                
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
