/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.querys;

import com.api.api_java_mail.sendEmail;
import com.api.connection.DBConnection;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miguel.lopez4757
 */
public class Cumpleanios {

    public static void obtenerCumpleaños() throws Exception {
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            String query = "SELECT c.nombre ||' '||c.apellido_paterno||' '||c.apellido_materno AS nombre, c.email correoCum "
                    + " FROM cumpleanios c "
                    + " WHERE EXTRACT(MONTH FROM c.fecha_cumpleanios) = EXTRACT(MONTH FROM CURRENT_DATE) "
                    + " AND EXTRACT(DAY FROM c.fecha_cumpleanios) = EXTRACT(DAY FROM CURRENT_DATE)";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String correoCum = resultSet.getString("correoCum");
                    System.out.println("¡Feliz cumpleaños a " + nombre + " " + correoCum + "!");

                    new sendEmail().sendMail("¡Feliz Cumpleaños! ",
                            "¡Hola " + nombre + " de parte de todos tus compañeros de UPSISAD te deseamos un feliz cumpleaños! :D", correoCum);
                }
            } catch (SQLException e) {
                System.out.println("NO HAY CUMPLEAÑERO");
            }
        }
    }

    public static void ObtenerCorreos() throws Exception {
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            String query = ""
                    + " SELECT   "
                    + "    c.email correo,   "
                    + "    nombres.nombre,   "
                    + "    to_char(current_date, 'DD ')||'de '||"
                    + "    CASE    "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 1 THEN 'Enero'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 2 THEN 'Febrero'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 3 THEN 'Marzo'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 4 THEN 'Abril'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 5 THEN 'Mayo'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 6 THEN 'Junio'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 7 THEN 'Julio'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 8 THEN 'Agosto'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 9 THEN 'Septiembre'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 10 THEN 'Octubre'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 11 THEN 'Noviembre'   "
                    + "        WHEN EXTRACT(MONTH FROM CURRENT_DATE) = 12 THEN 'Diciembre'   "
                    + "    END AS fecha_actual   "
                    + " FROM   "
                    + "    cumpleanios c   "
                    + " JOIN   "
                    + "    (   "
                    + "        SELECT   "
                    + "            nombre || ' ' || apellido_paterno || ' ' || apellido_materno AS nombre,   "
                    + "            ROW_NUMBER() OVER () AS row_num   "
                    + "        FROM   "
                    + "            cumpleanios   "
                    + "        WHERE   "
                    + "            EXTRACT(MONTH FROM fecha_cumpleanios) = EXTRACT(MONTH FROM CURRENT_DATE)   "
                    + "            AND EXTRACT(DAY FROM fecha_cumpleanios) = EXTRACT(DAY FROM CURRENT_DATE)   "
                    + "    ) AS nombres   "
                    + " ON   "
                    + "    TRUE   "
                    + " WHERE   "
                    + "    EXTRACT(MONTH FROM c.fecha_cumpleanios) <> EXTRACT(MONTH FROM CURRENT_DATE)   "
                    + "    AND EXTRACT(DAY FROM c.fecha_cumpleanios) <> EXTRACT(DAY FROM CURRENT_DATE) ";

            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                String nombre = resultSet.getString("nombre");
                String fecha = resultSet.getString("fecha_actual");
                List<String> correos = new ArrayList<>();
                
                while (resultSet.next()) {
                    String correo = resultSet.getString("correo");
                    System.out.println(correo + nombre + fecha);
                    correos.add(correo);
                    

                }
                new sendEmail().sendMailBCC("¡Cumpleaños del día! ",
                    "Hoy " + fecha + " es el cumpleaños de " + nombre + " ¡Deseale un feliz cumpleaños!", correos);
            } catch (SQLException e) {
                System.out.println("NO HAY CUMPLEAÑERO");
            }

            
        }
    }

}
