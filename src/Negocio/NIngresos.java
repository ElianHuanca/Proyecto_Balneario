/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DIngresos;
import Dato.DUsuarios;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author ELIAN
 */
public class NIngresos {
    private DIngresos dIngreso;
    private DUsuarios dUsuario;

    public NIngresos(){
        dIngreso = new DIngresos();
        dUsuario = new DUsuarios();
    }
    //no incluimos al id
    public void guardar(List<String> parametros, String email) throws SQLException, ParseException {
        int usuarioId = dUsuario.getIdByEmail(email);
        if(usuarioId != -1){
            dIngreso.guardar(parametros.get(0), usuarioId);
            dIngreso.desconectar();
        }
        dUsuario.desconectar();
    }

    //si incluimos id 
    /*public void modificar(List<String> parametros) throws SQLException, ParseException{
        dIngreso.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), parametros.get(2), (int) Float.parseFloat(parametros.get(3)));
        dIngreso.desconectar();
    }
    //Integer.parseInt(parametros.get(0))
    //Float.parseFloat(parametros.get(2)));
    
    //solo incluimos el id 
    public void eliminar(List<String> parametros) throws SQLException{
        dIngreso.eliminar(Integer.parseInt(parametros.get(0)));
        dIngreso.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> productos =  (ArrayList<String[]>) dIngreso.listar();
        dIngreso.desconectar();
        return productos;
    }

    public void guardar(List<String> ingreso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public void guardar(List<String> pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
