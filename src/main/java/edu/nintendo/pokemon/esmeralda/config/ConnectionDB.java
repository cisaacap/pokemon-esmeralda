package main.java.edu.nintendo.pokemon.esmeralda.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection connection = null;

    private ConnectionDB() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                connection = DriverManager.getConnection(CredentialsDB.URL, CredentialsDB.USER, CredentialsDB.PASSWORD);
                System.out.println("¡Conexión exitosa a MySQL!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de MySQL -> " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión a la Base de Datos -> " + e.getMessage());
        }
        return connection;
    }
}