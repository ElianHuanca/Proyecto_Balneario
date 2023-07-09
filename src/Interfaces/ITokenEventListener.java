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
    void user(TokenEvent event);   
    void client(TokenEvent event);     
    void dpto(TokenEvent event);            
    void social(TokenEvent event);      
    void schedule(TokenEvent event);    
    void notify(TokenEvent event);
    void apartment(TokenEvent event); 
    void visit(TokenEvent event); 
    void support(TokenEvent event);
    void reserve(TokenEvent event);
    
    void usuario(TokenEvent event);
    void producto(TokenEvent event);    
    void error(TokenEvent event);
    //agregar mas casos de uso
}
