/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import proyecto_balneario.SqlConnection;

/**
 *
 * @author ELIAN
 */
public class DUsuarios {

    private SqlConnection connection;

    public DUsuarios() {
        connection = new SqlConnection("postgres", "huanca1962", "127.0.0.1", "5432", "balnearioDB");
    }

    public void guardar(String ci, String nombre, String fecha_nacimiento, String email, String password, String rol) throws SQLException {
        String query = "INSERT INTO usuarios(ci,nombre,fecha_nacimiento,email,password,rol)"
                + "values(?,?,?,?,?,?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, ci);
        ps.setString(2, nombre);
        ps.setString(3, fecha_nacimiento);
        ps.setString(4, email);
        ps.setString(5, password);
        ps.setString(6, rol);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }

    public void modificar(int id,String ci, String nombre, String fecha_nacimiento, String email, String password, String rol) throws SQLException {
        String query = "UPDATE usuarios SET ci=?, nombre=?, fecha_nacimiento=?, email=?, password=?, rol=?"
                + "WHERE id=?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, ci);
        ps.setString(2, nombre);
        ps.setString(3, fecha_nacimiento);
        ps.setString(4, email);
        ps.setString(5, password);
        ps.setString(6, rol);
        ps.setInt(7,id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al modificar un usuario modificar()");
            throw new SQLException();
        }
    }

    public void eliminar(int id) throws SQLException{
        String query="DELETE FROM usuarios WHERE id=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java dice: "
                    + "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() {
        return null;
    }

    public String[] ver() {
        return null;
    }

    public void desconectar() {
        if (connection != null) {
            connection.closeConnection();
        }
    }

}