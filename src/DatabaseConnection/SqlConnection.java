/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import Config.env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Elian
 */
public class SqlConnection {
    private static final String DRIVER = "jdbc:postgresql://";
    
    private Connection connection;
    private String user;
    private String password;
    private String host;
    private String port;
    private String database;
    private String url;

    public SqlConnection() {
        this.user = env.user;
        this.password = env.password;
        this.host = env.host;
        this.port = env.port;
        this.database = env.database;
        
        this.url = DRIVER + host + ":" + port + "/" + database;
    }
    
    public SqlConnection(String user, String password, String host, String port, String database) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.database=database;
        
        this.url = DRIVER + host + ":" + port + "/" + database ;//127.0.0.1:5432//tecno_bd
    }   

    public Connection connect(){
        try {
            connection=DriverManager.getConnection(url,user,password);
        } catch (SQLException ex) {
            System.err.println("Class SQLConnection.java dice: "
            + "Ocurrio un error al momento de establecer una conexion connect()");
        }
        return connection;
    }    
    
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Class SQLConnection.java dice: "
                    + "Ocurrio un error al momento de cerrar la conexion closeConnection()");
        }
    }
}
