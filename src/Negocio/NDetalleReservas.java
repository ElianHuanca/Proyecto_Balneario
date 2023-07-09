/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DDetalleReservas;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NDetalleReservas {
    private final DDetalleReservas dDetalleReservas;

    public NDetalleReservas(){
        dDetalleReservas = new DDetalleReservas();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dDetalleReservas.guardar( Integer.parseInt(parametros.get(0)), Integer.parseInt(parametros.get(1)));
        dDetalleReservas.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dDetalleReservas.modificar(Integer.parseInt(parametros.get(0)),  Integer.parseInt(parametros.get(1)), Integer.parseInt(parametros.get(2)));
        dDetalleReservas.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dDetalleReservas.eliminar(Integer.parseInt(parametros.get(0)));
        dDetalleReservas.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dDetalleReservas.listar();
        dDetalleReservas.desconectar();
        return usuarios;
    }
}
