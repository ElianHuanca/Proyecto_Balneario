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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ELIAN
 */
public class PruebaInterpreter {

    public static void main(String[] args) {
        
//        USUARIOS
//        String get = "usuarios get ";
//        String post = "usuarios post [9638521;Maicol;2000-02-02;maicol@gmail.com;123456;cliente]";
//        String put = "usuarios put [4;12345;Maicol;2000-02-02;maicol@gmail.com;123456;cliente]";
//        String delete = "usuarios delete [4]";
        
//        TIPOSMEMBRESIAS
//        String get = "tiposmembresias get ";
//        String post = "tiposmembresias post [Diamante;1000 Boletos;600;9 Meses]";
//        String put = "tiposmembresias put [4;DINAMITA;1000 Boletos;600;9 Meses]";
//        String delete = "tiposmembresias delete [4]";
       
//       MEMBRESIAS
//            String get = "membresias get ";
//            String post = "membresias post [2023-02-01;2023-05-01;3;1]";
//            String put = "membresias put [3;2025-02-01;2025-05-01;3;1]";
//            String delete = "membresias delete [3]";

//        AMBIENTES
//        String get = "ambientes get ";
//        String post = "ambientes post [Cancha De Futbol; 100; 12]";
//        String put = "ambientes put [6;Cancha De Tennis; 100; 12]";
//        String delete = "ambientes delete [6]";

//        RESERVAS
//        String get = "reservas get ";
//        String post = "reservas post [2023-07-10;Tarde;3]";
//        String put = "reservas put [3;2024-07-10;Madrugada;3]";
//        String delete = "reservas delete [3]";

//        DETALLE_RESERVAS
//        String get = "detallereservas get ";
//        String post = "detallereservas post [1;4]";
//        String put = "detallereservas put [5;1;3]";
//        String delete = "detallereservas delete [5]";
        
//        PRODUCTOS 
//        String get = "productos get ";
//        String post = "productos post [Coca Cola 2Lts; 10]";
//        String put = "productos put [5;Coca Cola 3Lts; 15]";
//        String delete = "productos delete [5]";

//        USOS 
//        String get = "usos get ";
//        String post = "usos post [2023-07-10;4;4;1]";
//        String put = "usos put [5;2024-07-10;8;2;2]";
//        String delete = "usos delete [5]";

//        INGRESOS 
//        String get = "ingresos get ";
//        String post = "ingresos post [Diamante;1000 Boletos;600;9 Meses]";
//        String put = "ingresos put [4;DINAMITA;1000 Boletos;600;9 Meses]";
//        String delete = "ingresos delete [4]";

//        PAGOS 
        String get = "pagos get ";
        String post = "pagos post [Diamante;1000 Boletos;600;9 Meses]";
        String put = "pagos put [4;DINAMITA;1000 Boletos;600;9 Meses]";
        String delete = "pagos delete [4]";

        String correo = "huancacori@gmail.com";
        
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

        Interpreter interpreter = new Interpreter(delete, correo);
        interpreter.setListener(new ITokenEventListener() {    

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

            @Override
            public void comandos(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
}
