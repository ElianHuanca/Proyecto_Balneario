/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DUsuarios;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NUsuarios {

    private DUsuarios dUsuarios;

    public NUsuarios(){
        dUsuarios = new DUsuarios();
    }
    
    public void guardar(List<String> parametros) throws SQLException, ParseException{
        dUsuarios.guardar(parametros.get(0), parametros.get(1), parametros.get(2), parametros.get(3), parametros.get(4),parametros.get(5));
        dUsuarios.desconectar();
    }
    
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        dUsuarios.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), parametros.get(2), parametros.get(3), parametros.get(4),parametros.get(5),parametros.get(6));
        dUsuarios.desconectar();
    }
    
    public void eliminar(List<String> parametros) throws SQLException{
        dUsuarios.eliminar(Integer.parseInt(parametros.get(0)));
        dUsuarios.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> usuarios =  (ArrayList<String[]>) dUsuarios.listar();
        dUsuarios.desconectar();
        return usuarios;
    }
}
