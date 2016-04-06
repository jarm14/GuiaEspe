package com.example.espeguiada;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<SECCION> mSeccionTable;
    private MobileServiceTable<SUBSECCION> mSubSeccionTable;

    //Offline Sync
    /**
     * Mobile Service Table used to acce/home/adrianss and Sync data
     */
    //private MobileServiceSyncTable<ToDoItem> mToDoTable;

    /**
     * Adapter to sync the items list with the view
     */
    private ToDoItemAdapter mAdapter;

    private SECCION_adapter seccionAdapter;

    /**
     * EditText containing the "New To Do" text
     */
    //private EditText mTextNewToDo;

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

        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);

        rectorado = new ArrayList<String>();
        viceAcad = new ArrayList<String>();
        viceAdm = new ArrayList<String>();
        viceDoc = new ArrayList<String>();
        viceInv = new ArrayList<String>();
        departamentos = new ArrayList<String>();
        otros = new ArrayList<String>();

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://espeguiada.azurewebsites.net",
                    this).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use

            mSeccionTable = mClient.getTable(SECCION.class);
            mSubSeccionTable = mClient.getTable(SUBSECCION.class);

            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

            //Init local storage
            initLocalStore().get();

            //mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);

            // Create an adapter to bind the items with the view
            //mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
            //ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
            //listViewToDo.setAdapter(mAdapter);

            // Load the items from the Mobile Service
            //refreshItemsFromTable();
            exp_sections=(ExpandableListView)findViewById(R.id.expandableListView);
            SeparateLists();
            prepareListData();
            listAdapter=new SECCION_adapter(sections, subSections, this);
            exp_sections.setAdapter(listAdapter);

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }
    }

    /**
     * Initializes the activity menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Select an option from the menu
     */
    /*@Override*/
    /*public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            refreshItemsFromTable();
        }

        return true;
    }*/

    /**
     * Mark an item as completed
     *
     * @param item
     *            The item to mark
     *
    public void checkItem(final ToDoItem item) {
        if (mClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    checkItemInTable(item);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (item.isComplete()) {
                                mAdapter.remove(item);
                            }
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);

    }*/

    /**
     * Mark an item as completed in the Mobile Service Table
     *
     * @param SeccionItem
     *            The item to mark
     */
    public void checkItemInTableSeccion(SECCION SeccionItem) throws ExecutionException, InterruptedException {
        mSeccionTable.update(SeccionItem).get();
    }

    public void checkItemInTableSubSeccion(SUBSECCION SubSeccionItem) throws ExecutionException, InterruptedException {
        mSubSeccionTable.update(SubSeccionItem).get();
    }

    /**
     * Add a new item
     *
     * @param view
     *            The view that originated the call
     *
    public void addItem(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final ToDoItem item = new ToDoItem();

        item.setText(mTextNewToDo.getText().toString());
        item.setComplete(false);subSections.put(sections.get(7),viceAcad);
        subSections.put(sections.get(8),viceAcad);

        subSections.put(sections.get(11),viceAcad);

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final ToDoItem entity = addItemInTable(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!entity.isComplete()){
                                mAdapter.add(entity);
                            }
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);

        mTextNewToDo.setText("");
    }*/

    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     *
    public ToDoItem addItemInTable(ToDoItem item) throws ExecutionException, InterruptedException {
        ToDoItem entity = mToDoTable.insert(item).get();
        return entity;
    }*/

    /**
     * Refresh the list with the items in the Table
     *
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<ToDoItem> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (ToDoItem item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }*/

    /**
     * Refresh the list with the items in the Mobile Service Table
     */

    private List<SECCION> refreshItemsSeccionTable() throws ExecutionException, InterruptedException {

        return mSeccionTable.select().execute().get();
        /*return mSeccionTable.where().field("complete").
                eq(val(false)).execute().get();*/
    }

    private List<SUBSECCION> refreshItemsSubSeccionTable() throws ExecutionException, InterruptedException {

        return mSubSeccionTable.select().execute().get();
        /*return mToDoTable.where().field("complete").
                eq(val(false)).execute().get();*/
    }

    private void SeparateLists()
    {

        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                try

                {
                    final List<SUBSECCION> itemsSubSeccion = refreshItemsSubSeccionTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (SUBSECCION items : itemsSubSeccion) {
                                switch (items.getSEC_ID()) {
                                    case 1:
                                        rectorado.add(items.getSUB_NOMBRE().toString());
                                    case 2:
                                        viceAdm.add(items.getSUB_NOMBRE().toString());
                                    case 3:
                                        viceDoc.add(items.getSUB_NOMBRE().toString());
                                    case 4:
                                        departamentos.add(items.getSUB_NOMBRE().toString());
                                    case 5:
                                        viceInv.add(items.getSUB_NOMBRE().toString());
                                    case 6:
                                        viceAcad.add(items.getSUB_NOMBRE().toString());
                                    case 7:
                                        otros.add(items.getSUB_NOMBRE().toString());
                                }
                            }
                        }
                    });

                } catch (Exception e){
                    createAndShowDialog(e,"ERROR");
                }
                return null;
            }
        }.execute();
    }

    private void prepareListData() {
        sections = new ArrayList<String>();
        subSections = new HashMap<String, List<String>>();

        try {
            List<SECCION> itemsSeccion=refreshItemsSeccionTable();
            for(SECCION items:itemsSeccion){
                sections.add(items.getSEC_NOMBRE());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*sections.add("Rectorado");
        sections.add("Vicerectorado Administrativo");

        rectorado.add("Honorable consejo universitario");
        rectorado.add("Secretaría general");
        viceAdm.add("Unidad de Talento humano");
        viceAdm.add("Unidad Financiera");*/

        subSections.put(sections.get(0),rectorado);
        subSections.put(sections.get(1),viceAdm);
        subSections.put(sections.get(2),viceDoc);
        subSections.put(sections.get(3),departamentos);
        subSections.put(sections.get(4),viceInv);
        subSections.put(sections.get(5),viceAcad);
        subSections.put(sections.get(6),otros);




    }

    //Offline Sync
    /**
     * Refresh the list with the items in the Mobile Service Sync Table
     */
    /*private List<ToDoItem> refreshItemsFromMobileServiceTableSyncTable() throws ExecutionException, InterruptedException {
        //sync the data
        sync().get();
        Query query = QueryOperations.field("complete").
                eq(val(false));
        return mToDoTable.read(query).get();
    }*/

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    //Offline Sync
    /**
     * Sync the current context and the Mobile Service Sync Table
     * @return
     */
    /*
    private AsyncTask<Void, Void, Void> sync() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MobileServiceSyncContext syncContext = mClient.getSyncContext();
                    syncContext.push().get();
                    mToDoTable.pull(null).get();
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        return runAsyncTask(task);
    }
    */

    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
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



    private class ProgressFilter implements ServiceFilter {

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
    }

}