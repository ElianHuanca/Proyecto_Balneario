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
public class DMembresias {
    
    private final SqlConnection connection;

    public DMembresias() {
        connection = new SqlConnection();
    }

    public void guardar(String fecha_ini, String fecha_fin, int idUsuario,int idTipoMembresia ) throws SQLException, ParseException {
        String query = "INSERT INTO membresias(fecha_ini,fecha_fin,idUsuario,idTipoMembresia)"
                + "values(?,?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);        
        ps.setDate(1, DateString.StringToDateSQL(fecha_ini));
        ps.setDate(2, DateString.StringToDateSQL(fecha_fin));
        ps.setInt(3, idUsuario);
        ps.setInt(4, idTipoMembresia);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMembresias.java dice: "
                    + "Ocurrio un error al insertar una membresia guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id,String fecha_ini, String fecha_fin, int idUsuario,int idTipoMembresia) throws SQLException, ParseException {
        String query = "UPDATE membresias SET fecha_ini=?, fecha_fin=?, idUsuario=?, idTipoMembresia=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setDate(1, DateString.StringToDateSQL(fecha_ini));
        ps.setDate(2, DateString.StringToDateSQL(fecha_fin));
        ps.setInt(3, idUsuario);
        ps.setInt(4, idTipoMembresia);
        ps.setInt(5,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMembresias.java dice: "
                    + "Ocurrio un error al modificar una membresia modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM membresias WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMembresias.java dice: "
                    + "Ocurrio un error al eliminar una membresia eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM membresias";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("fecha_ini"),
                set.getString("fecha_fin"),
                set.getString("idUsuario"),                
                set.getString("idTipoMembresia")
            });
        }
        return usuarios;
    }

    public String[] ver(int id) throws SQLException {
        String[] usuario = null;
        String query = "SELECT * FROM membresias WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            usuario = new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("fecha_ini"),
                set.getString("fecha_fin"),
                set.getString("idUsuario"),                
                set.getString("idTipoMembresia")
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
