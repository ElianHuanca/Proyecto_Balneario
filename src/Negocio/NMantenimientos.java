/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DMantenimientos;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NMantenimientos {
    private final DMantenimientos dMantenimientos;

    public NMantenimientos(){
        dMantenimientos = new DMantenimientos();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMantenimientos.guardar(parametros.get(0),Float.parseFloat(parametros.get(1)));
        dMantenimientos.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMantenimientos.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1),Float.parseFloat(parametros.get(2)));
        dMantenimientos.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dMantenimientos.eliminar(Integer.parseInt(parametros.get(0)));
        dMantenimientos.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dMantenimientos.listar();
        dMantenimientos.desconectar();
        return usuarios;
    }
}
