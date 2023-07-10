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
public class DIngresos {
    public static final String[] HEADERS =
    {"ID","FECHA","USUARIO_ID"};
    
    private SqlConnection connection;

    public DIngresos(){
        connection = new SqlConnection();
    }

    public void guardar(String fecha, int usuarioId) throws SQLException, ParseException {
        String query = "INSERT INTO ingresos(fecha,usuario_id)"
                + "values(?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setDate(1, DateString.StringToDateSQL(fecha));
        ps.setInt(2, usuarioId);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DIngreso.java dice: "
                    + "Ocurrio un error al insertar un ingreso guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id, String fecha, int usuarioId) throws SQLException, ParseException {
        String query = "UPDATE ingresos SET fecha=?, hora=?, usuarioId=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setDate(1, DateString.StringToDateSQL(fecha));
        ps.setFloat(2,usuarioId);
        ps.setInt(3,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DIngreso.java dice: "
                    + "Ocurrio un error al modificar un Producto modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM ingresos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DIngreso.java dice: "
                    + "Ocurrio un error al eliminar un producto eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String query = "SELECT * FROM ingresos";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            productos.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion"),               
                String.valueOf(set.getFloat("precio"))
            });
        }
        return productos;
    }

    public String[] ver(int id) throws SQLException {
        String[] producto = null;
        String query = "SELECT * FROM ingresos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            producto = new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion"),                
                String.valueOf(set.getFloat("precio"))
            };
        }        
        return producto;
    }
    

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }
}
