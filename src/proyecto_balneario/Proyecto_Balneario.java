/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_balneario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ELIAN
 */
public class Proyecto_Balneario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            SqlConnection sqlConnection
                    = new SqlConnection("postgres", "huanca1962", "127.0.0.1", "5432", "balnearioDB");

            String query = "SELECT * FROM usuarios WHERE id = 1";
            PreparedStatement ps = sqlConnection.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("resultado: " + rs.next());
            
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto_Balneario.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

}
