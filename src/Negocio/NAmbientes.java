/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DAmbientes;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NAmbientes {
    private final DAmbientes dAmbientes;

    public NAmbientes(){
        dAmbientes = new DAmbientes();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dAmbientes.guardar(parametros.get(0), Float.parseFloat(parametros.get(1)), Integer.parseInt(parametros.get(2)));
        dAmbientes.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dAmbientes.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), Float.parseFloat(parametros.get(2)), Integer.parseInt(parametros.get(3)));
        dAmbientes.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dAmbientes.eliminar(Integer.parseInt(parametros.get(0)));
        dAmbientes.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dAmbientes.listar();
        dAmbientes.desconectar();
        return usuarios;
    }
}
