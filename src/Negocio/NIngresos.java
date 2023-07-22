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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELIAN
 */
public class NIngresos {

    private DIngresos dIngreso;
    // private DUsuarios dUsuario;

    public NIngresos() {
        dIngreso = new DIngresos();
        //dUsuario = new DUsuarios();
    }

    //no incluimos al id
    public void guardar(List<String> parametros) throws SQLException, ParseException {
        //int usuarioId = dUsuario.getIdByEmail(email);  
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dIngreso.guardar(parametros.get(0), Integer.parseInt(parametros.get(1)));
        dIngreso.desconectar();
    }

    public void modificar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dIngreso.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), Integer.parseInt(parametros.get(2)));
        dIngreso.desconectar();
    }

    public void eliminar(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dIngreso.eliminar(Integer.parseInt(parametros.get(0)));
        dIngreso.desconectar();
    }

    public ArrayList<String[]> listar() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dIngreso.listar();
        dIngreso.desconectar();
        return usuarios;
    }
}
