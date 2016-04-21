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

    /*Variable para la coneccion sql*/
    Connection coneccion;

    /**
     * Adapter to sync the items list with the view
     */
    private SECCION_adapter seccionAdapter;


    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;


    private Connection CONN ()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        String azure = "jdbc:jtds:sqlserver://lugaresespe.database.windows.net:1433;database=LugaresEspe;user=GuiaEspe@lugaresespe;password=Admin112358.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        String joel ="jdbc:jtds:sqlserver://10.101.24.36:1433;database=LugaresEspe;user=sa;password=Joelram5635726.;loginTimeout=30;";

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(joel);

        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        return conn;
    }

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

            coneccion = CONN();

            exp_sections=(ExpandableListView)findViewById(R.id.expandableListView);
            SeparateLists();
            prepareListData();
            listAdapter=new SECCION_adapter(sections, subSections, this);
            exp_sections.setAdapter(listAdapter);
          //  coneccion.close();

        } catch (Exception e) {
            createAndShowDialog(e, "Error");
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

                switch(subSections.get(sections.get(groupPosition)).get(childPosition)){
                    case "Secretaria General":
                        subseccion=new SUBSECCION("1","1","1","Secretaría general", "La Secretaría general se encarga de aldsfkjhalsknclkajsdhflkashdfkljadshlkfhasldkfhasljkfdlajfdhladksjfhlaksjfhladskjfhlakfhasdlfjsk" +
                                "lfhlkaHDLKSDlkjHADLKJSDJkhlhlJDHLJSAHDLASJFHLDJAHLFSJDHGLKJDGLJSHLlaksjdfhlajfh lajshflkaj flkjasdfhlkjdashf klasdfh alksdfh alsfh ", "ubicacion_coordenadas", R.drawable.map_secretaria_general ,false );
                        break;
                    case "Unidad de Auditoria Interna":
                        //subseccion=new SUBSECCION("2","1","1","Honorable consejo universitario", "El Honorable consejo universitario se encarga de  flkjasdfhlkjdashf klasdfh alksdfh alsfh ", "ubicacion_coordenadas",null, 0, false );
                        subseccion=getSubseccionItem(subSections.get(sections.get(groupPosition)).get(childPosition));
                        break;
                    case "Unidad de Talento humano":
                        subseccion=new SUBSECCION("1","2","1","Unidad de Talento humano", "La Unidad de Talento humano se encarga de  flkjasdfhlkjdashf klasdfh alksdfh alsfh ", "ubicacion_coordenadas",0 ,false );
                        break;
                    case "Unidad Financiera":
                        subseccion=new SUBSECCION("2","2","1","Unidad Financiera", "La Unidad Financiera se encarga de  flkjasdfhlkjdashf klasdfh alksdfh alsfh ", "ubicacion_coordenadas",0, false );
                        break;
                    default:
                        subseccion=new SUBSECCION("1","1","1","Vacio", "vacio ", "ubicacion_coordenadas",0, false );
                        break;

                }

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
            Statement statement = coneccion.createStatement();
            itemSubseccion = statement.executeQuery(sql);
                itemSubseccion.next();
                subseccion = new SUBSECCION(itemSubseccion.getString("id"), itemSubseccion.getString("SEC_ID"), itemSubseccion.getString("EDI_ID"), itemSubseccion.getString("SUB_NOMBRE"), itemSubseccion.getString("SUB_DESCRIPCION"), itemSubseccion.getString("SUB_UBICACION"), Integer.parseInt(itemSubseccion.getString("MAP_RESOURCE")), Boolean.parseBoolean(itemSubseccion.getString("deleted")));
                return subseccion;



        }catch(SQLException se){
            System.out.println("Error: " + se.toString());
        }

        return null;

    }


    private void SeparateLists() {

        ResultSet itemsSubseccion;
        String sql = "use LugaresEspe; select * from subseccion";

        try {

            Statement statement = coneccion.createStatement();
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
            Statement statement = coneccion.createStatement();
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



    /*private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }*/
    final static String ACT_INFO="com.example.espeguiada.ActivityInfo";
    public void nextActivity(View view, SUBSECCION subseccion){

        String[] info=new String[3];



        info[0]=subseccion.getSUB_NOMBRE().toString();
        info[1]=subseccion.getSUB_DESCRIPCION().toString();
        //info[2]=subseccion.getMAP_RESOURCE();


        Intent act=new Intent(this,ActivityInfo.class);
        act.putExtra(ACT_INFO, info);
        act.putExtra("Map",subseccion.getMAP_RESOURCE());
        startActivity(act);
    }

}