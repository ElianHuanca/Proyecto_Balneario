/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DPagos;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELIAN
 */
public class NPagos {
    private DPagos dPago;

    public NPagos(){
        dPago = new DPagos();
    }
    //no incluimos al id
    public void guardar(List<String> parametros) throws SQLException, ParseException {
    dPago.guardar(parametros.get(0), Float.parseFloat(parametros.get(1)),parametros.get(2));
    dPago.desconectar();
    }

    //si incluimos id 
    public void modificar(List<String> parametros) throws SQLException, ParseException{
        dPago.modificar( Integer.parseInt(parametros.get(0)), parametros.get(1), Float.parseFloat(parametros.get(2)), parametros.get(3));
        dPago.desconectar();
    }
    //tipo pago
    //monto_total
    //fecha
    
    //Integer.parseInt(parametros.get(0))
    //Float.parseFloat(parametros.get(2)));
    
    
    public void eliminar(List<String> parametros) throws SQLException{
        dPago.eliminar(Integer.parseInt(parametros.get(0)));
        dPago.desconectar();
    }
    
    public ArrayList<String[]> listar() throws SQLException{
        ArrayList<String[]> pagos =  (ArrayList<String[]>) dPago.listar();
        dPago.desconectar();
        return pagos;
    }
}
