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
import Negocio.NAmbientes;
import Negocio.NDetalleReservas;
import Negocio.NIngresos;
import Negocio.NMembresias;
import Negocio.NPagos;
import Negocio.NProductos;
import Negocio.NReservas;
import Negocio.NTiposMembresias;
import Negocio.NUsos;
import Negocio.NUsuarios;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyecto_balneario.PruebaInterpreter;
import utils.Email;
/**
 *
 * @author Elian
 */
public class ConnectionCore {
    public static void main(String[] args) {
        
        /*MailVerificationThread mail = new MailVerificationThread();
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
        thread.start();*/
        
        Email emailObject = new Email("huancacori@gmail.com", Email.SUBJECT,
                "Petición realizada correctamente");
        
        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }
    
    public static void interprete(Email email) {        
        
        //Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());       
        NUsuarios nUsuarios = new NUsuarios();
        NTiposMembresias nTiposMembresias=new NTiposMembresias();
        NMembresias nMembresias = new NMembresias();
        NAmbientes nAmbientes = new NAmbientes();
        NReservas nReservas = new NReservas ();
        NDetalleReservas nDetalleReservas = new NDetalleReservas ();
        NProductos nProductos = new NProductos ();
        NUsos nUsos = new NUsos ();
        NPagos nPagos = new NPagos();
        NIngresos nIngresos = new NIngresos();

        Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());
        interpreter.setListener(new ITokenEventListener() {

            @Override
            public void usuario(TokenEvent event) {

            }

            @Override
            public void producto(TokenEvent event) {
                
            }

            @Override
            public void usuarios(TokenEvent event) {
                System.out.println("CU: USUARIOS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nUsuarios.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nUsuarios.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nUsuarios.modificar(event.getParams());
                        System.out.println("Datos De Usuario Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nUsuarios.eliminar(event.getParams());
                        System.out.println("Usuario Eliminado Con Exito");
                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void tiposMembresias(TokenEvent event) {
                System.out.println("CU: TIPOSMEMBRESIAS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nTiposMembresias.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nTiposMembresias.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nTiposMembresias.modificar(event.getParams());
                        System.out.println("Datos De tiposMembresias Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nTiposMembresias.eliminar(event.getParams());
                        System.out.println("tiposMembresias Eliminado Con Exito");
                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void membresias(TokenEvent event) {
                System.out.println("CU: MEMBRESIAS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nMembresias.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nMembresias.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nMembresias.modificar(event.getParams());
                        System.out.println("Datos De membresias Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nMembresias.eliminar(event.getParams());
                        System.out.println("membresias Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void ambientes(TokenEvent event) {
                System.out.println("CU: AMBIENTES");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nAmbientes.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nAmbientes.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nAmbientes.modificar(event.getParams());
                        System.out.println("Datos De ambientes Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nAmbientes.eliminar(event.getParams());
                        System.out.println("ambientes Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void reservas(TokenEvent event) {
                System.out.println("CU: RESERVAS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nReservas.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nReservas.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nReservas.modificar(event.getParams());
                        System.out.println("Datos De reservas Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nReservas.eliminar(event.getParams());
                        System.out.println("reservas Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void detalle_reservas(TokenEvent event) {
                System.out.println("CU: DETALLE_RESERVAS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nDetalleReservas.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nDetalleReservas.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nDetalleReservas.modificar(event.getParams());
                        System.out.println("Datos De detalle_reservas Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nDetalleReservas.eliminar(event.getParams());
                        System.out.println("detalle_reservas Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void productos(TokenEvent event) {
                System.out.println("CU: PRODUCTOS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nProductos.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nProductos.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nProductos.modificar(event.getParams());
                        System.out.println("Datos De Productos Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nProductos.eliminar(event.getParams());
                        System.out.println("Productos Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void usos(TokenEvent event) {
                System.out.println("CU: USOS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nUsos.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nUsos.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nUsos.modificar(event.getParams());
                        System.out.println("Datos De Uso Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nUsos.eliminar(event.getParams());
                        System.out.println("Uso Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void pagos(TokenEvent event) {
                System.out.println("CU: PAGOS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nPagos.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nPagos.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nPagos.modificar(event.getParams());
                        System.out.println("Datos De Pago Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nPagos.eliminar(event.getParams());
                        System.out.println("Pago Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void ingresos(TokenEvent event) {
                System.out.println("CU: INGRESOS");
                /*System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = nIngresos.listar();

                        String s = "";
                        for (int i = 0; i < lista.size(); i++) {
                            s = s + "[" + i + "] : ";
                            for (int j = 0; j < lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    } else if (event.getAction() == Token.POST) {
                        nIngresos.guardar(event.getParams());
                        System.out.println("Guardado con exito");
                    } else if (event.getAction() == Token.PUT) {
                        nIngresos.modificar(event.getParams());
                        System.out.println("Datos De Uso Modificado Con Exito");
                    } else if (event.getAction() == Token.DELETE) {
                        nIngresos.eliminar(event.getParams());
                        System.out.println("Uso Eliminado Con Exito");                        
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }*/
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
