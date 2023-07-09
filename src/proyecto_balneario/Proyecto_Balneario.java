/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_balneario;

import ConnectionCore.MailVerificationThread;
import Interfaces.IEmailEventListener;
import Negocio.NUsuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Email;

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
        /*try {
            SqlConnection sqlConnection
                    = new SqlConnection("postgres", "huanca1962", "127.0.0.1", "5432", "balnearioDB");

            String query = "SELECT * FROM usuarios WHERE id = 1";
            PreparedStatement ps = sqlConnection.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("resultado: " + rs.next());
            
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto_Balneario.class.getName()).log(Level.SEVERE,null,ex);
        }*/
        usuario();
        
        /*MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                for (Email email: emails) {
                    System.out.println(email);
        
        MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                for (Email email : emails) {
                    System.out.println(email);
                    //interprete(email);
                }
            }
        });
        
        Thread thread= new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();*/
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
        
        //usuario();
    }
    
    public static void usuario() {
        NUsuarios nu = new NUsuarios();
        /*List<String> usuario = new ArrayList<String>();
        usuario.add("9648312");
        usuario.add("Elian Huanca");
        usuario.add("2000-05-02");
        usuario.add("huancacori@gmail.com");
        usuario.add("123456");
        usuario.add("Administrador");
        
        usuario.add("9648307");
        usuario.add("Diana Paniagua");
        usuario.add("1999-08-17");
        usuario.add("diana@gmail.com");
        usuario.add("123456");
        usuario.add("Administrador");
        
        try {
            nu.guardar(usuario);                        
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto_Balneario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Proyecto_Balneario.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        ArrayList<String[]> usuarios = new ArrayList<String[]>();
        try {
            usuarios=nu.listar();
            for (int i = 0; i < usuarios.size(); i++) {
                for (int j = 0; j < usuarios.get(i).length; j++) {
                    System.out.print(usuarios.get(i)[j] + " | ");
                }
                System.out.println("");
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto_Balneario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
