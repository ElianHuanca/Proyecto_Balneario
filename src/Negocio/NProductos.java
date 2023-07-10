/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DProductos;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elian
 */
public class NProductos {

    private final DProductos dProductos;

    public NProductos() {
        dProductos = new DProductos();
    }

    public void guardar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dProductos.guardar(parametros.get(0), Float.parseFloat(parametros.get(1)));
        dProductos.desconectar();
    }

    public void modificar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dProductos.modificar(Integer.parseInt(parametros.get(0)), parametros.get(1), Float.parseFloat(parametros.get(2)));
        dProductos.desconectar();
    }

    public void eliminar(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parametros vacios!");
        }
        dProductos.eliminar(Integer.parseInt(parametros.get(0)));
        dProductos.desconectar();
    }

    public ArrayList<String[]> listar() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dProductos.listar();
        dProductos.desconectar();
        return usuarios;
    }
}
