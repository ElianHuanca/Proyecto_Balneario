/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ConnectionCore.MailVerificationThread;
import ConnectionCore.SendEmailThread;
import Dato.DAmbientes;
import Dato.DComandos;
import Dato.DDetalleReservas;
import Dato.DIngresos;
import Dato.DMembresias;
import Dato.DPagos;
import Dato.DProductos;
import Dato.DReservas;
import Dato.DTiposMembresias;
import Dato.DUsos;
import Dato.DUsuarios;
import Interfaces.IEmailEventListener;
import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Token;
import Interpreter.TokenEvent;
import Negocio.NAmbientes;
import Negocio.NComandos;
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
import utils.Email;
import utils.HtmlBuilder;

/**
 *
 * @author ELIAN
 */
public class MailApplication implements IEmailEventListener, ITokenEventListener {

    private static final int CONSTRAINTS_ERROR = -2;
    private static final int NUMBER_FORMAT_ERROR = -3;
    private static final int INDEX_OUT_OF_BOUND_ERROR = -4;
    private static final int PARSE_ERROR = -5;
    private static final int AUTHORIZATION_ERROR = -6;

    private MailVerificationThread mailVerificationThread;

    private NUsuarios nUsuarios;
    private NTiposMembresias nTiposMembresias;
    private NMembresias nMembresias;
    private NAmbientes nAmbientes;
    private NReservas nReservas;
    private NDetalleReservas nDetalleReservas;
    private NProductos nProductos;
    private NUsos nUsos;
    private NPagos nPagos;
    private NIngresos nIngresos;
    private NComandos nComandos;

    public MailApplication() {
        mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(MailApplication.this);
        nUsuarios = new NUsuarios();
        nTiposMembresias = new NTiposMembresias();
        nMembresias = new NMembresias();
        nAmbientes = new NAmbientes();
        nReservas = new NReservas();
        nDetalleReservas = new NDetalleReservas();
        nProductos = new NProductos();
        nUsos = new NUsos();
        nPagos = new NPagos();
        nIngresos = new NIngresos();
        nComandos = new NComandos();
    }

    public void start() {
        Thread thread = new Thread(mailVerificationThread);
        thread.setName("Mail Verfication Thread");
        thread.start();
    }

    @Override
    public void onReceiveEmailEvent(List<Email> emails) {
        for (Email email : emails) {
            Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());
            interpreter.setListener(MailApplication.this);
            Thread thread = new Thread(interpreter);
            thread.setName("Interpreter Thread");
            thread.start();
        }
    }

    @Override
    public void error(TokenEvent event) {
        handleError(event.getAction(), event.getSender(), event.getParams());
    }

    private void handleError(int type, String email, List<String> args) {
        Email emailObject = null;

        switch (type) {
            case Token.ERROR_CHARACTER:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Caracter desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "El caracter \"" + args.get(1) + "\" es desconocido."
                }));
                break;
            case Token.ERROR_COMMAND:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Comando desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "No se reconoce la palabra \"" + args.get(1) + "\" como un comando válido"
                }));
                break;
            case CONSTRAINTS_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al interactuar con la base de datos",
                    "Referencia a información inexistente"
                }));
                break;
            case NUMBER_FORMAT_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error en el tipo de parámetro",
                    "El tipo de uno de los parámetros es incorrecto"
                }));
                break;
            case INDEX_OUT_OF_BOUND_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Cantidad de parámetros incorrecta",
                    "La cantidad de parámetros para realizar la acción es incorrecta"
                }));
                break;
            case PARSE_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al procesar la fecha",
                    "La fecha introducida posee un formato incorrecto"
                }));
                break;
            case AUTHORIZATION_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Acceso denegado",
                    "Usted no posee los permisos necesarios para realizar la acción solicitada"
                }));
                break;
        }
        sendEmail(emailObject);
    }

    private void simpleNotifySuccess(String email, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            "Petición realizada correctamente",
            message
        }));
        sendEmail(emailObject);
    }

    private void simpleNotify(String email, String title, String topic, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            title, topic, message
        }));
        sendEmail(emailObject);
    }

    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTable(title, headers, data));
        sendEmail(emailObject);
    }

    private void tableGraficaSuccess(String email, String title, ArrayList<String[]> data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateGrafica(title, data));
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTableForSimpleData(title, headers, data));
        sendEmail(emailObject);
    }

    private void sendEmail(Email email) {
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }

    @Override
    public void usuarios(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Usuarios", DUsuarios.HEADERS, nUsuarios.listar());
                    break;
                case Token.POST:
                    nUsuarios.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Usuario Guardado Correctamente");
                    break;
                case Token.PUT:
                    nUsuarios.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Usuario Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nUsuarios.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Usuario Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void tiposMembresias(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de TiposMembresias", DTiposMembresias.HEADERS, nTiposMembresias.listar());
                    break;
                case Token.POST:
                    nTiposMembresias.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "TiposMembresia Guardado Correctamente");
                    break;
                case Token.PUT:
                    nTiposMembresias.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "TiposMembresia Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nTiposMembresias.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "TiposMembresia Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void membresias(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Membresias", DMembresias.HEADERS, nMembresias.listar());                   
                    break;
                case Token.POST:
                    nMembresias.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Membresia Guardado Correctamente");
                    break;
                case Token.PUT:
                    nMembresias.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Membresia Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nMembresias.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Membresia Eliminado Correctamente");
                    break;
                case Token.GRAFICA:
                    tableGraficaSuccess(event.getSender(), "Grafica De Consumo De Membresias", nMembresias.listarGrafica());
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ambientes(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Ambientes", DAmbientes.HEADERS, nAmbientes.listar());
                    break;
                case Token.POST:
                    nAmbientes.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Ambiente Guardado Correctamente");
                    break;
                case Token.PUT:
                    nAmbientes.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Ambiente Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nAmbientes.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Ambiente Eliminado Correctamente");
                    break;
                case Token.GRAFICA:
                    tableGraficaSuccess(event.getSender(), "Grafica De Reservas De Ambientes", nMembresias.listarGrafica());
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reservas(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Reservas", DReservas.HEADERS, nReservas.listar());
                    break;
                case Token.POST:
                    nReservas.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Reserva Guardado Correctamente");
                    break;
                case Token.PUT:
                    nReservas.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Reserva Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nReservas.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Reserva Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void detalle_reservas(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de DetalleReservas", DDetalleReservas.HEADERS, nDetalleReservas.listar());
                    break;
                case Token.POST:
                    nDetalleReservas.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "DetalleReserva Guardado Correctamente");
                    break;
                case Token.PUT:
                    nDetalleReservas.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "DetalleReserva Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nDetalleReservas.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "DetalleReserva Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void productos(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Productos", DProductos.HEADERS, nProductos.listar());
                    break;
                case Token.POST:
                    nProductos.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Producto Guardado Correctamente");
                    break;
                case Token.PUT:
                    nProductos.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Producto Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nProductos.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Producto Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void usos(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Usos", DUsos.HEADERS, nUsos.listar());
                    break;
                case Token.POST:
                    nUsos.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Uso Guardado Correctamente");
                    break;
                case Token.PUT:
                    nUsos.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Uso Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nUsos.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Uso Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ingresos(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Ingresos", DIngresos.HEADERS, nIngresos.listar());
                    break;
                case Token.POST:
                    nIngresos.guardar(event.getParams(), event.getSender());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Ingreso Guardado Correctamente");
                    break;
                case Token.PUT:
                    nIngresos.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Ingreso Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nIngresos.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Ingreso Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pagos(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista de Pagos", DPagos.HEADERS, nPagos.listar());
                    break;
                case Token.POST:
                    nPagos.guardar(event.getParams());
                    System.out.println("Guardado con exito");
                    simpleNotifySuccess(event.getSender(), "Pago Guardado Correctamente");
                    break;
                case Token.PUT:
                    nPagos.modificar(event.getParams());
                    System.out.println("Modificado con exito");
                    simpleNotifySuccess(event.getSender(), "Pago Modificado Correctamente");
                    break;
                case Token.DELETE:
                    nPagos.eliminar(event.getParams());
                    System.out.println("Eliminado con exito");
                    simpleNotifySuccess(event.getSender(), "Pago Eliminado Correctamente");
                    break;
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void comandos(TokenEvent event) {
        try {
            switch (event.getAction()) {
                case Token.GET:
                    tableNotifySuccess(event.getSender(), "Lista De Comandos", DComandos.HEADERS, nComandos.listar());
                    break;
//                case Token.GET:
//                    tableNotifySuccess(event.getSender(), "Lista de Pagos", DPagos.HEADERS, nPagos.listar());
//                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
