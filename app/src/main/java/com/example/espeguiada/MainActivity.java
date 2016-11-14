package com.example.espeguiada;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import android.content.Context;


import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.*;


public class MainActivity extends Activity {


    HashMap<String,List<String>> subSections;
    List<String> sections;
    ExpandableListView exp_sections;
    ExpandableListAdapter listAdapter;
    List<String>rectorado;
    List<String>viceAdm;
    List<String>viceDoc;
    List<String>viceInv;
    List<String>viceAcad;
    List<String>departamentos;
    List<String>otros;

    /*Variable para la conexion sql*/
    Connection conexion;
    Connection conexion1;

    /**
     * Adapter to sync the items list with the view
     */
    private SECCION_adapter seccionAdapter;


    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;




    /**
     * Initializes the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);


        //mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        //mProgressBar.setVisibility(ProgressBar.GONE);

        rectorado = new ArrayList<String>();
        viceAcad = new ArrayList<String>();
        viceAdm = new ArrayList<String>();
        viceDoc = new ArrayList<String>();
        viceInv = new ArrayList<String>();
        departamentos = new ArrayList<String>();
        otros = new ArrayList<String>();

        try {

            conexion = ConexionSQL.ConnectionHelper();

            exp_sections=(ExpandableListView)findViewById(R.id.expandableListView);
            SeparateLists();
            prepareListData();
            listAdapter=new SECCION_adapter(sections, subSections, this);
            exp_sections.setAdapter(listAdapter);
          //  coneccion.close();

        } catch (Exception e) {
            createAndShowDialog("No se pudo conectar al servidor. Revise su conexión a Internet.", "Error");
        }

        //exp_sections=(ExpandableListView)findViewById(R.id.expandableListView);
        //SeparateLists();
        //prepareListData();
        //listAdapter=new SECCION_adapter(sections, subSections, this);
       // exp_sections.setAdapter(listAdapter);

        exp_sections.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SUBSECCION subseccion;

                String subsecName = subSections.get(sections.get(groupPosition)).get(childPosition);

                subseccion=getSubseccionItem(subsecName);



                nextActivity(v,subseccion);
                return false;
            }
        });
    }

    /**
     * Initializes the activity menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    private SUBSECCION getSubseccionItem(String name){
        ResultSet itemSubseccion;
        SUBSECCION subseccion;
        String sql="use LugaresEspe; select * from subseccion where SUB_NOMBRE='"+name+"'";
        try{
            Statement statement = conexion.createStatement();
            itemSubseccion = statement.executeQuery(sql);
            itemSubseccion.next();
            subseccion = new SUBSECCION(itemSubseccion.getString("id"), itemSubseccion.getString("SEC_ID"), itemSubseccion.getString("EDI_ID"), itemSubseccion.getString("SUB_NOMBRE"), itemSubseccion.getString("SUB_DESCRIPCION"), itemSubseccion.getString("SUB_UBICACION"),itemSubseccion.getString("SUB_INSTRUCCIONES"), itemSubseccion.getString("SUB_LOGO"), itemSubseccion.getString("SUB_MICROSITIO"));
            return subseccion;



        }catch(SQLException se){
            System.out.println("Error: " + "No se pudo conectar al servidor. Revise su conexión a Internet.");
        }



        return null;

    }

   /* private String getCoord(String edi_id){
        ResultSet Coord;
        String coordinates;
        String sql1="use LugaresEspe; select EDI_UBICACION from EDIFICIO where id='"+edi_id+"'";
        try{
            Statement statement = conexion.createStatement();
            Coord = statement.executeQuery(sql1);
            Coord.next();
            coordinates=Coord.getString("EDI_UBICACION");
            return coordinates;



        }catch(SQLException se){
            System.out.println("Error: " + "No se pudo conectar al servidor. Revise su conexión a Internet.");
        }



        return null;

    }*/



    private DIRECTORDEP getDirectorItem(String Subseccionid){
        ResultSet itemDirector;
        DIRECTORDEP director;
        String sub;

        String sql="use LugaresEspe; select * from DIRECTORDEP where SUB_ID='"+Subseccionid+"'";
        try{
            Statement statement = conexion.createStatement();
            itemDirector = statement.executeQuery(sql);
            itemDirector.next();

            director = new DIRECTORDEP();
            director.setId(itemDirector.getString("id"));
            director.setSUB_ID(itemDirector.getString("SUB_ID"));
            director.setDIR_NOMBRE(itemDirector.getString("DIR_NOMBRE"));
            director.setDIR_APELLIDO(itemDirector.getString("DIR_APELLIDO"));
            director.setDIR_MAIL(itemDirector.getString("DIR_MAIL"));
            director.setDIR_TELEFONO(itemDirector.getString("DIR_TELEFONO"));
            director.setDIR_ABTITULO(itemDirector.getString("DIR_ABTITULO"));

            return director;

        }catch(SQLException se){
            System.out.println("Error: " + "No se pudo conectar al servidor. Revise su conexión a Internet.");
        }

        return null;

    }


    private void SeparateLists() {

        ResultSet itemsSubseccion;
        String sql = "use LugaresEspe; select * from subseccion";

        try {

            Statement statement = conexion.createStatement();
            itemsSubseccion = statement.executeQuery(sql);
            // Ajuste nuestro SimpleAdapter
            int id;
            id = 0;

            while (itemsSubseccion.next()) {
                // navegar por nuestro ResultSet en cada registro, siempre y cuando exista un prox.

                id = Integer.parseInt(itemsSubseccion.getString("SEC_ID"));

                switch (id) {
                    case 1:
                        rectorado.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 2:
                        viceAdm.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 3:
                        viceDoc.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 4:
                        departamentos.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 5:
                        viceInv.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 6:
                        viceAcad.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                    case 7:
                        otros.add(itemsSubseccion.getString("SUB_NOMBRE"));
                        break;
                }
            }

        } catch (SQLException se) {
            System.out.println("Error: " + se.toString());
        }
    }

    private void prepareListData() {
        sections = new ArrayList<String>();
        subSections = new HashMap<String, List<String>>();
        ResultSet itemsSeccion;
        String sql = "use LugaresEspe; select * from seccion";

        try
        {
            Statement statement = conexion.createStatement();
            itemsSeccion = statement.executeQuery(sql);
            int id=0;

            while (itemsSeccion.next())
            {
                sections.add(itemsSeccion.getString("SEC_NOMBRE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        subSections.put(sections.get(0),rectorado);
        subSections.put(sections.get(1),viceAdm);
        subSections.put(sections.get(2),viceDoc);
        subSections.put(sections.get(3),departamentos);
        subSections.put(sections.get(4),viceInv);
        subSections.put(sections.get(5),viceAcad);
        subSections.put(sections.get(6),otros);

    }





    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }




    final static String ACT_INFO="com.example.espeguiada.ActivityInfo";

    public void nextActivity(View view, SUBSECCION subseccion) {

        DIRECTORDEP director = getDirectorItem(subseccion.getId().toString());

        if(director==null)
        {
            director = new DIRECTORDEP();
        }

        String[] info = new String[8];

        if (subseccion.getSUB_NOMBRE() == null)
        {
            subseccion.setSUB_NOMBRE(" ");
        }

        if(subseccion.getSUB_DESCRIPCION()==null)
        {
            subseccion.setSUB_DESCRIPCION(" ");
        }

        if(subseccion.getSUB_LOGO()==null)
        {
            subseccion.setSUB_LOGO(" ");
        }

        if(director.getDIR_ABTITULO()==null)
        {
            director.setDIR_ABTITULO(" ");
        }

        if(director.getDIR_APELLIDO()==null)
        {
            director.setDIR_APELLIDO(" ");
        }

        if(director.getDIR_NOMBRE()==null)
        {
            director.setDIR_NOMBRE(" ");
        }

        if(director.getDIR_MAIL()==null)
        {
            director.setDIR_MAIL(" ");
        }

        if(director.getDIR_TELEFONO()==null)
        {
            director.setDIR_TELEFONO(" ");
        }

        if(subseccion.getSUB_MICROSITIO()==null)
        {
            subseccion.setSUB_MICROSITIO(" ");
        }

        info[0]=subseccion.getSUB_NOMBRE().toString();
        info[1]=subseccion.getSUB_DESCRIPCION().toString();
        info[2]=subseccion.getSUB_LOGO();
        info[3] = director.getDIR_ABTITULO().toString() + ". " + director.getDIR_NOMBRE().toString() + " " + director.getDIR_APELLIDO().toString();
        info[4] = director.getDIR_MAIL().toString();
        info[5] = director.getDIR_TELEFONO().toString();
        info[6]=subseccion.getSUB_MICROSITIO().toString();
        info[7]= subseccion.getSUB_UBICACION().toString();

        Intent act=new Intent(this,ActivityInfo.class);
        act.putExtra(ACT_INFO, info);
        //act.putExtra("Map",subseccion.getMAP_RESOURCE());
        startActivity(act);
    }

}