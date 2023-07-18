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
public class DPagos {
    public static final String[] HEADERS =
    {"ID","TIPO_PAGO","MONTO_TOTAL","FECHA"};

    private SqlConnection connection;

    public DPagos() {
        connection = new SqlConnection();
    }

    public void guardar( String tipo_pago, float monto_total, String fecha ) throws SQLException, ParseException {
        String query = "INSERT INTO pagos( tipo_pago,monto_total,fecha )"
                + "values(?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, tipo_pago);
        ps.setFloat(2, monto_total);
        ps.setDate(3, DateString.StringToDateSQL(fecha));

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPagos.java dice: "
                    + "Ocurrio un error al insertar un pago guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id ,String tipo_pago, float monto_total, String fecha) throws SQLException, ParseException {
        String query = "UPDATE pagos SET tipo_pago=?,monto_total=?, fecha=? "
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, tipo_pago);
        ps.setFloat(2, monto_total);
        ps.setDate(3, DateString.StringToDateSQL(fecha));
        ps.setInt(4,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPagos.java dice: "
                    + "Ocurrio un error al modificar un pago modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM pagos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPagos.java dice: "
                    + "Ocurrio un error al eliminar un pago eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String query = "SELECT * FROM pagos";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            productos.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("tipo_pago"),
                String.valueOf(set.getFloat("monto_total")),
                set.getString("fecha"),
            });
        }
        return productos;
    }

    public String[] ver(int id) throws SQLException {
        String[] pago = null;
        String query = "SELECT * FROM pagos WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            pago = new String[] {
                String.valueOf(set.getInt("id")),
                String.valueOf("tipo_pago"),
                String.valueOf(set.getFloat("monto_total")),
                set.getString("fecha")
            };
        }        
        return pago;
    }

    
    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }
}
