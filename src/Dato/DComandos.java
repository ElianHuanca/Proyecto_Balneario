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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELIAN
 */
public class DComandos {
    public static final String[] HEADERS =
    {"ID","CU","ACCION","PARAMETROS","EJEMPLO"};
    
    private final SqlConnection connection;

    public DComandos() {
        connection = new SqlConnection();
    }
    
    public List<String[]> listar() throws SQLException {
        List<String[]> comandos = new ArrayList<>();
        String query = "SELECT * FROM comandos";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            comandos.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("cu"),                              
                set.getString("accion"),
                set.getString("parametros"),
                set.getString("ejemplo"),
            });
        }
        return comandos;
    }
    
    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }
}
