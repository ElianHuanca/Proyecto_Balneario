/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;



import Dato.DUsuarios;
import Interfaces.IEmailEventListener;
import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Token;
import Interpreter.TokenEvent;
import Negocio.NUsuarios;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Email;
/**
 *
 * @author Elian
 */
public class ConnectionCore {
    public static void main(String[] args) {
        
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
        
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
        
        /*Email emailObject = new Email("ronaldorivero3@gmail.com", Email.SUBJECT,
                "Petición realizada correctamente");
        
        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();*/
    }
    
    /*public static void interprete(Email email) {
        //DProductos bProducto = new DProducto();
        NUsuarios dUsuario = new NUsuarios();
        
        Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());       
        interpreter.setListener(new ITokenEventListener() {            

            @Override
            public void usuario(TokenEvent event) {
                System.out.println("CU: USUARIO");
                System.out.println(event);    
                try {
                    if(event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = dUsuario.listar();
                        
                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
                }
            }

            /*@Override
            public void producto(TokenEvent event) {
                System.out.println("CU: MASCOTA");
                System.out.println(event);
                try {
                    if(event.getAction() == Token.AGREGAR) {
                        bProducto.guardar(event.getParams(), event.getSender());   
                        System.out.println("OK");
                        //notificar al usuario que se ejecuto su comando
                    } else if(event.getAction() == Token.MODIFY) {

                    } else if(event.getAction() == Token.DELETE) {

                    } else {
                        System.out.println("La accion no es valida para el caso de uso");
                        //enviar al correo una notificacion
                    }                
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
                }
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println("DESCONOCIDO");
                System.out.println(event);
                //enviar una notificacion
            }

            @Override
            public void producto(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void usuarios(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void tiposMembresias(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void membresias(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void ambientes(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reservas(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void detalle_reservas(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void productos(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void usos(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }*/
}
