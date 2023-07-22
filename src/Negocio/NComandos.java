/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DComandos;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ELIAN
 */
public class NComandos {
    private final DComandos dComandos;

    public NComandos(){
        dComandos = new DComandos();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> comandos =  (ArrayList<String[]>) dComandos.listar();
        dComandos.desconectar();
        return comandos;
    }
}
