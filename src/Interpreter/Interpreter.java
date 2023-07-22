/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpreter;

import Interfaces.ITokenEventListener;



/**
 *
 * @author ELIAN
 */
public class Interpreter implements Runnable{
    private ITokenEventListener listener;
    private Analex analex;
    
    private String command;
    private String sender;
    
    public Interpreter(String command, String sender) {
        this.command = command;
        this.sender = sender;        
    }

    public ITokenEventListener getListener() {
        return listener;
    }

    public void setListener(ITokenEventListener listener) {
        this.listener = listener;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    private void filterEvent(TokenCommand token_command){
        TokenEvent token_event = new TokenEvent(this, sender);        
        token_event.setAction(token_command.getAction());
        
        int count_params = token_command.countParams();
        for(int i = 0; i < count_params; i++){
            int pos = token_command.getParams(i);
            token_event.addParams(analex.getParam(pos));
        }
        
        //ajustar de acuerdo a su casos de uso
        switch(token_command.getName()){                                         
            case Token.USUARIOS:
                listener.usuarios(token_event);
                break;
            case Token.TIPOSMEMBRESIAS:
                listener.tiposMembresias(token_event);
                break;
            case Token.PAGOS:
                listener.pagos(token_event);
                break;
            case Token.MEMBRESIAS:
                listener.membresias(token_event);
                break;
            case Token.AMBIENTES:
                listener.ambientes(token_event);
                break;
            case Token.RESERVAS:
                listener.reservas(token_event);
                break;
            case Token.DETALLERESERVAS:
                listener.detalle_reservas(token_event);
                break;
            case Token.PRODUCTOS:
                listener.productos(token_event);
                break;
            case Token.USOS:
                listener.usos(token_event);
                break;
            case Token.INGRESOS:
                listener.ingresos(token_event);
                break;
            case Token.COMANDOS:
                listener.comandos(token_event);
                break;
                
        }
        
    }
    
    private void tokenError(Token token, String error){
        TokenEvent token_event = new TokenEvent(this, sender);
        token_event.setAction(token.getAttribute());
        token_event.addParams(command);
        token_event.addParams(error);
        listener.error(token_event);
    }
    
    @Override
    public void run() {
        analex = new Analex(command);
        TokenCommand token_command = new TokenCommand();
        Token token;
        
        //while(analex.Preanalisis() != null) {
            //token = analex.Preanalisis();
            //if (token.getName() == Token.END && token.getName() == Token.ERROR) {
                //break;
            //}
        //}
        
        while((token = analex.Preanalisis()).getName() != Token.END && token.getName() != Token.ERROR){
            if(token.getName() == Token.CU){
                token_command.setName(token.getAttribute());// id del CU
            } else if(token.getName() == Token.ACTION){
                token_command.setAction(token.getAttribute());// id de la accion
            } else if(token.getName() == Token.PARAMS){
                token_command.addParams(token.getAttribute());// la posicion del parametro en el tsp
            }
            analex.next();
        }
        
        if(token.getName() == Token.END){
            filterEvent(token_command);// se analizo el comando con exito
        } else if(token.getName() == Token.ERROR){
            tokenError(token, analex.lexeme()); // se produjo un error en el analisis
        }
        
    }
}
