/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author miguel.lopez4757
 */
public class DBConnection {

    private static Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "root";

    // Datos de conexión SSH
    private static final String SSH_HOST = "148.202.33.61";
    private static final String SSH_USER = "lisunza";
    private static final String SSH_PASSWORD = "L1zuns4*21";
    private static final int SSH_PORT = 7530; // Puerto SSH predeterminado
    //private static final String SSH_KEY_FILE = "/ruta/a/tu/llave_ssh"; // Opcional: Si utilizas una llave SSH

    public static Connection getConnection() {
        if (connection == null) {
            createSSHTunnel(); // Crea el túnel SSH antes de conectar a la base de datos

            try {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createSSHTunnel() {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);
            session.setPassword(SSH_PASSWORD); // Si utilizas contraseña SSH
            session.setConfig("StrictHostKeyChecking", "no"); // Opcional: Si no quieres verificar el host
            session.connect();

            int localPort = 5432; // Puerto local para el túnel
            String remoteHost = "localhost"; // El host de la base de datos en el servidor remoto (tú mismo, ya que el túnel redirige al localhost)
            int remotePort = 5432; // Puerto de la base de datos en el servidor remoto (el mismo que el local en este caso)

            session.setPortForwardingL(localPort, remoteHost, remotePort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
