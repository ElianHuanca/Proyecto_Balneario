/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Interpreter.TokenEvent;

/**
 *
 * @author ELIAN
 */
public interface ITokenEventListener {    
    
    void usuario(TokenEvent event);
    void producto(TokenEvent event);    
    void error(TokenEvent event);
    
    void usuarios(TokenEvent event);
    void tiposMembresias(TokenEvent event);
    void membresias(TokenEvent event);
    void ambientes(TokenEvent event);
    void reservas(TokenEvent event);
    void detalle_reservas(TokenEvent event);
    void productos(TokenEvent event);
    void usos(TokenEvent event);
    void ingresos(TokenEvent event);
    void pagos(TokenEvent event);    
    //agregar mas casos de uso
}
