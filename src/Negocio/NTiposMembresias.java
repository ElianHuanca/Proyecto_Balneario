/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DTiposMembresias;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NTiposMembresias {
    private final DTiposMembresias dTiposMembresias;

    public NTiposMembresias(){
        dTiposMembresias = new DTiposMembresias();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dTiposMembresias.guardar(parametros.get(0), parametros.get(1), Float.parseFloat(parametros.get(2)), parametros.get(3));
        dTiposMembresias.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dTiposMembresias.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), parametros.get(2), Float.parseFloat(parametros.get(3)), parametros.get(4));
        dTiposMembresias.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dTiposMembresias.eliminar(Integer.parseInt(parametros.get(0)));
        dTiposMembresias.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dTiposMembresias.listar();
        dTiposMembresias.desconectar();
        return usuarios;
    }
}
