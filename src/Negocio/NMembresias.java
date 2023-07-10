/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DMembresias;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NMembresias {
    private final DMembresias dMembresias;

    public NMembresias(){
        dMembresias = new DMembresias();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMembresias.guardar(parametros.get(0), parametros.get(1), Integer.parseInt(parametros.get(2)), Integer.parseInt(parametros.get(3)));
        dMembresias.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMembresias.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), parametros.get(2), Integer.parseInt(parametros.get(3)), Integer.parseInt(parametros.get(4)));
        dMembresias.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMembresias.eliminar(Integer.parseInt(parametros.get(0)));
        dMembresias.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dMembresias.listar();
        dMembresias.desconectar();
        return usuarios;
    }
}
