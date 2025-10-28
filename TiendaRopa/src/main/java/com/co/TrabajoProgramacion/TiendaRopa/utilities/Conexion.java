package com.co.TrabajoProgramacion.TiendaRopa.utilities;

import java.sql.*;

public class Conexion {

    private Connection con;

    // Método para obtener la conexión
    public Connection obtenerConexion() {
        try {
            // 🔹 Cambia estos valores según tu configuración local
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Tienda_Ropa;encrypt=false;";
            String usuario = "sa"; //usuario de SQL Server
            String contrasena = "JL123456"; // contraseña

            // 🔹 No hace falta Class.forName() en Java moderno si el driver está en tu pom.xml
            con = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("✅ Conexión exitosa a SQL Server");
        } catch (SQLException ex) {
            System.err.println("❌ Error al conectar a la base de datos:");
            ex.printStackTrace();
        }
        return con;
    }

    // Método principal de prueba
    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        try (Connection conn = conexion.obtenerConexion()) {
            if (conn != null) {
                String query = "SELECT * FROM Clientes";

                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    if (!rs.isBeforeFirst()) {
                        System.out.println("⚠️ NO HAY DATOS EN LA TABLA ESTUDIANTE");
                    } else {
                        while (rs.next()) {
                            System.out.println(
                                    "ID: " + rs.getString("ClienteId") +
                                            " | Nombre: " + rs.getString("Nombre")
                            );
                        }
                    }

                } catch (SQLException e) {
                    System.err.println("❌ Error ejecutando la consulta: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se pudo establecer la conexión (conn == null).");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error cerrando la conexión: " + e.getMessage());
        }
    }
}
