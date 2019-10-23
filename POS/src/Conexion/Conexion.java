package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private String BaseDatos = "Programacionll";
    private String Usuario = "root";
    private String Contraseña = "manuel2502";
    private String Url = "jdbc:mysql://localhost/" + BaseDatos;
    private Connection conn = null;

    public Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(this.Url, this.Usuario, this.Contraseña);
            if (conn != null) {
                System.out.println("CONEXION ESTABLECIDA CON BASE DE DATOS " + this.BaseDatos + "  ");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    public Connection getConn() {
        return conn;
    }
}
