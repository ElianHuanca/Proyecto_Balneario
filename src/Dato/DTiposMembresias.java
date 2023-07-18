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
public class DTiposMembresias {
    
    public static final String[] HEADERS = 
        {"ID","NOMBRE", "DESCRIPCION", "PRECIO", "DURACION"};
    
    
    private final SqlConnection connection;

    public DTiposMembresias() {
        connection = new SqlConnection();
    }

    public void guardar(String nombre, String descripcion, float precio,String duracion ) throws SQLException, ParseException {
        String query = "INSERT INTO tiposMembresias(nombre,descripcion,precio,duracion)"
                + "values(?,?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);        
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setFloat(3, precio);
        ps.setString(4, duracion);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DTiposMembresias.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id,String nombre, String descripcion, float precio,String duracion) throws SQLException, ParseException {
        String query = "UPDATE tiposMembresias SET nombre=?, descripcion=?, precio=?, duracion=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setFloat(3, precio);
        ps.setString(4, duracion);
        ps.setInt(5,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DTiposMembresias.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM tiposMembresias WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DTiposMembresias.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM tiposMembresias";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),
                set.getString("descripcion"),
                String.valueOf(set.getFloat("precio")),
                set.getString("duracion"),                                
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM tiposMembresias WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            usuario = new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),
                set.getString("descripcion"),
                String.valueOf(set.getFloat("precio")),
                set.getString("duracion"), 
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
