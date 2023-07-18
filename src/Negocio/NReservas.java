/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DReservas;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NReservas {
    private final DReservas dReservas;

    public NReservas(){
        dReservas = new DReservas();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dReservas.guardar(parametros.get(0),parametros.get(1), Integer.parseInt(parametros.get(2)),Integer.parseInt(parametros.get(3)));
        dReservas.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dReservas.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), parametros.get(2), Integer.parseInt(parametros.get(3)) ,Integer.parseInt(parametros.get(4)));
        dReservas.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dReservas.eliminar(Integer.parseInt(parametros.get(0)));
        dReservas.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dReservas.listar();
        dReservas.desconectar();
        return usuarios;
    }
}
