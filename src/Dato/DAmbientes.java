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
public class DAmbientes {
    
    public static final String[] HEADERS
            = {"ID", "NOMBRE", "PRECIO","CAPACIDAD"};
    
    private final SqlConnection connection;

    public DAmbientes() {
        connection = new SqlConnection();
    }

    public void guardar(String nombre, float precio, int capacidad ) throws SQLException, ParseException {
        String query = "INSERT INTO ambientes(nombre,precio,capacidad)"
                + "values(?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);        
        ps.setString(1, nombre);
        ps.setFloat(2, precio);
        ps.setInt(3, capacidad);        

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DAmbientes.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id,String nombre, float precio, int capacidad) throws SQLException, ParseException {
        String query = "UPDATE ambientes SET nombre=?, precio=?, capacidad=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setFloat(2, precio);
        ps.setInt(3, capacidad);        
        ps.setInt(4,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DAmbientes.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM ambientes WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DAmbientes.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM ambientes";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),
                String.valueOf(set.getFloat("precio")),
                String.valueOf(set.getInt("capacidad")),                
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM ambientes WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            usuario = new String[] {
                String.valueOf(set.getInt("id")),                
                set.getString("nombre"),
                String.valueOf(set.getFloat("precio")),
                String.valueOf(set.getInt("capacidad")),  
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
