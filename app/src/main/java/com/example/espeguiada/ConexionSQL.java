package com.example.espeguiada;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by adrian on 10/05/16.
 */
public class ConexionSQL {
    public static Connection ConnectionHelper() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

                ConnectionURL = "jdbc:jtds:sqlserver://190.10.161.195:1433;database=LugaresEspe;user=sa;password=Joelram5635726.;loginTimeout=5;";
                //jdbc:jtds:sqlserver://190.10.161.195:1433;database=LugaresEspe;user=sa;password=Joelram5635726.;loginTimeout=5;
                connection = DriverManager.getConnection(ConnectionURL);

        } catch (SQLException se) {
            //Log.e("ERROR", se.getMessage());

        } catch (ClassNotFoundException e) {
            //Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            //Log.e("ERROR", e.getMessage());
        }

        if (connection == null) {
            try {

                ConnectionURL = "jdbc:jtds:sqlserver://10.9.9.175:1433;database=LugaresEspe;user=usguia;password=Admin112358.;loginTimeout=1;";
                connection = DriverManager.getConnection(ConnectionURL);

            } catch (SQLException se) {
                Log.e("ERROR", "No se pudo conectar a la base de datos. Revise su conexión a internet.");

            }catch (Exception e) {
                Log.e("ERROR", "No se pudo conectar.");
            }

        }

        return connection;
    }
}
