package id.ac.umn.budimanputrajaya;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity implements AppRecyclerAdapter.OnAppListener {
    private AppMonstaLoader appMonstaLoader;
    private RecyclerView appRecyclerView;
    private AppRecyclerAdapter appRecyclerAdapter;
    private ArrayList<POJOApplication> appList;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ConstraintLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // START SETUP TOOLBAR
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.about:
                        Intent intent = new Intent(DataActivity.this, AboutActivity.class);
                        startActivity(intent);
                }
                return false;
            }
        });
        // END SETUP TOOLBAR

        // START SETUP PROGRESSBAR
        progressLayout = findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);
        // END SETUP PROGRESSBAR

        // START SETUP LIST DATA
        appMonstaLoader = AppMonstaLoader.getInstance(this);
        appList = new ArrayList<>();

        setUpRecycler();
        downloadNewestApp();
        // END SETUP LIST DATA
    }

    private void setUpRecycler(){
        appRecyclerView = findViewById(R.id.recycler_app);
        appRecyclerAdapter = new AppRecyclerAdapter(this, appList, this);
        appRecyclerView.setAdapter(appRecyclerAdapter);
        appRecyclerView.addItemDecoration(new DividerItemDecoration(appRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        appRecyclerView.setLayoutManager(layout);
    }

    private void downloadNewestApp(){
        appMonstaLoader.getDetailsApps(new AppMonstaLoader.AppMonstaListener<ArrayList<POJOApplication>>() {
            @Override
            public void onAppDownloaded(ArrayList<POJOApplication> result) {
                appList = result;
                appRecyclerAdapter.setAppList(result);
                progressLayout.setVisibility(View.GONE);
            }

            @Override
            public void onErrorDownloading(String errorMessage) {
                Log.e("ERRORDOWNLOADING", errorMessage);
                progressLayout.setVisibility(View.GONE);
                new AlertDialog.Builder(DataActivity.this)
                        .setTitle("Sorry, there's something wrong")
                        .setMessage("Error message:\n"+errorMessage+"\n\nTry again?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // try get data again
                                progressLayout.setVisibility(View.VISIBLE);
                                downloadNewestApp();
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    // START INTERFACE METHOD
    @Override
    public void onAppClick(int position) {
        Intent intent = new Intent(DataActivity.this, DetailActivity.class);
        intent.putExtra("Application Data", appList.get(position));
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    @Override
    public void onAppLongClick(int position){
        POJOApplication appData = appList.get(position);
        Toast.makeText(DataActivity.this, "Success register " + appData.getName(), Toast.LENGTH_LONG).show();

        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.createNotification(appData.getName(), appData.getIconUrl(), position);
    }
    // END INTERFACE METHOD

    // START OPTION
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // END OPTION
}
