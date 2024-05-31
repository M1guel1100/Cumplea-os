/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.api.api_java_mail;

import com.api.connection.DBConnection;
import com.api.querys.Cumpleanios;
import java.sql.Connection;

/**
 *
 * @author miguel.lopez4757
 */
public class API_Java_Mail {

    public static void main(String[] args) throws Exception {
        // Obtener la conexión a la base de datos
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            System.out.println("Entra la conexión");
            // Realizar la consulta para obtener los nombres de las personas que cumplen años hoy
            Cumpleanios.obtenerCumpleaños();
            Cumpleanios.ObtenerCorreos();
            // Cerrar la conexión
            //DBConnection.closeConnection();
            
            System.exit(0);
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
           System.exit(0);
        }
    }
    
     //System
}
