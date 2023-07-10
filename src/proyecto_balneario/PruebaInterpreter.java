/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_balneario;

import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Token;
import Interpreter.TokenEvent;
import Negocio.NIngresos;
import Negocio.NPagos;
import Negocio.NUsuarios;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ELIAN
 */
public class PruebaInterpreter {

    public static void main(String[] args) {
        //AGREGAR USUARIO [Ronaldo, Rivero Gonzales, 76042142]
        //Nombre del caso uso | accion | parametros
        //[mascota|add|Firulay|25|Negro]
        //mascota add 
        //String comando = "producto agregar [Producto 200; 125]";
        String comando = "usuario get ";
        String correo = "huancacori@gmail.com";
        /*
        //{Firulay, 25, Negro} //
        //CU: String - producto
        //ACTION: String - agregar
        //PARAMETROS: List<String> [Firulay,25,Negro]
        
        String CU = "producto";
        String ACTION = "agregar";
        List<String> PARAMETROS = new ArrayList<>();
        PARAMETROS.add("Producto 100"); PARAMETROS.add("25");
        
        dUsuario dUsuario = new dUsuario();
        BProducto bProducto = new BProducto();
            try {
                if(CU == "usuario") {
                    if(ACTION == "agregar") {

                    } else if(ACTION == "modificar") {

                    } else if(ACTION == "eliminar") {

                    } else if(ACTION == "listar") {

                    } else if(ACTION == "ver") {

                    }            
                } else if(CU == "producto") {
                    if(ACTION == "agregar") {                        
                        bProducto.guardar(PARAMETROS, correo);
                        System.out.println("Guardado del producto exitoso");
                        //enviar un correo de notificacion al usuario
                    } else if(ACTION == "modificar") {

                    } else if(ACTION == "eliminar") {

                    } else if(ACTION == "listar") {

                    } else if(ACTION == "ver") {

                    } else {
                        System.out.println("Comando no recorrido");
                        //enviar un correo notificando el error
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
         */

        //BProducto bProducto = new BProducto();
        NUsuarios dUsuario = new NUsuarios();
        NPagos nPago = new NPagos();
        NIngresos nIngreso = new NIngresos();
        
        Interpreter interpreter = new Interpreter(comando, correo);
        interpreter.setListener(new ITokenEventListener() {

            @Override
            public void usuario(TokenEvent event) {
                System.out.println("CU: USUARIOS");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = dUsuario.listar();

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
                        dUsuario.guardar(event.getParams());
                    } else if (event.getAction() == Token.PUT) {
                        dUsuario.modificar(event.getParams());
                    } else if (event.getAction() == Token.DELETE) {
                        dUsuario.eliminar(event.getParams());
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: MASCOTA");
                System.out.println(event);
                /* {
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
                }*/
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println("DESCONOCIDO");
                System.out.println(event);
                //enviar una notificacion
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

            @Override
            public void pago(TokenEvent event) {
                System.out.println("CU: PAGO");
                System.out.println(event);
                try {
                    if (event.getAction() == Token.AGREGAR) {
                        nPago.guardar(event.getParams());
                        System.out.println("OK");
                        //notificar al pago que se ejecuto su comando
                    } else if (event.getAction() == Token.MODIFY) {

                    } else if (event.getAction() == Token.DELETE) {

                    } else {
                        System.out.println("La accion no es valida para el caso de uso");
                        //enviar al correo una notificacion
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void ingreso(TokenEvent event) {
                System.out.println("CU: INGRESO");
                System.out.println(event);
                try {
                    if(event.getAction() == Token.AGREGAR) {
                        nIngreso.guardar(event.getParams(), event.getSender());   
                        System.out.println("OK");
                        //notificar al ingreso que se ejecuto su comando
                    } else if(event.getAction() == Token.MODIFY) {

                    } else if(event.getAction() == Token.DELETE) {

                    } else {
                        System.out.println("La accion no es valida para el caso de uso");
                        //enviar al correo una notificacion
                    }                
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(PruebaInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
}
