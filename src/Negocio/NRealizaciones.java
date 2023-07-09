/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DRealizaciones;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NRealizaciones {

    private final DRealizaciones dRealizaciones;

    public NRealizaciones() {
        dRealizaciones = new DRealizaciones();
    }

    public void guardar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dRealizaciones.guardar(Integer.parseInt(parametros.get(0)), Integer.parseInt(parametros.get(1)), parametros.get(2));
        dRealizaciones.desconectar();
    }

    public void modificar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dRealizaciones.modificar(Integer.parseInt(parametros.get(0)), Integer.parseInt(parametros.get(1)), Integer.parseInt(parametros.get(2)), parametros.get(3));
        dRealizaciones.desconectar();
    }

    public void eliminar(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dRealizaciones.eliminar(Integer.parseInt(parametros.get(0)));
        dRealizaciones.desconectar();
    }

    public ArrayList<String[]> listar() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dRealizaciones.listar();
        dRealizaciones.desconectar();
        return usuarios;
    }
}
