/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DUsos;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NUsos {
    private final DUsos dUsos;

    public NUsos(){
        dUsos = new DUsos();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dUsos.guardar(parametros.get(0),Integer.parseInt(parametros.get(1)),Integer.parseInt(parametros.get(2)),Integer.parseInt(parametros.get(3)));
        dUsos.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dUsos.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1),Integer.parseInt(parametros.get(2)),Integer.parseInt(parametros.get(3)),Integer.parseInt(parametros.get(4)));
        dUsos.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dUsos.eliminar(Integer.parseInt(parametros.get(0)));
        dUsos.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dUsos.listar();
        dUsos.desconectar();
        return usuarios;
    }
}
