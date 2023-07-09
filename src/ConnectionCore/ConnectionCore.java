/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;



import Dato.DUsuarios;
import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Token;
import Interpreter.TokenEvent;
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
        /*
        MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                for (Email email : emails) {
                    //System.out.println(email);
                    interprete(email);
                }
            }
        });
        
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
        */
        Email emailObject = new Email("ronaldorivero3@gmail.com", Email.SUBJECT,
                "Petición realizada correctamente");
        
        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }
    
    public static void interprete(Email email) {
        DProductos bProducto = new DProducto();
        DUsuarios dUsuario = new DUsuarios();
        
        Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());       
        interpreter.setListener(new ITokenEventListener() {
            @Override
            public void user(TokenEvent event) {
                System.out.println("CU: USER");
                System.out.println(event);
            }

            @Override
            public void client(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void dpto(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void social(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void schedule(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void notify(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void apartment(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void visit(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void support(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reserve(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

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

            @Override
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
        });
        
        
        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
}
