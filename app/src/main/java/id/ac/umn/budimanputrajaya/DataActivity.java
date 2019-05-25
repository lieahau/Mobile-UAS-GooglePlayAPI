package id.ac.umn.budimanputrajaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity implements AppRecyclerAdapter.OnAppListener {
    private AppMonstaLoader appMonstaLoader;
    private RecyclerView appRecyclerView;
    private AppRecyclerAdapter appRecyclerAdapter;
    private ArrayList<POJOApplication> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        appMonstaLoader = AppMonstaLoader.getInstance(this);
        appList = new ArrayList<>();

        setUpRecycler();
        downloadNewestApp();
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
            }

            @Override
            public void onErrorDownloading(String errorMessage) {
                Log.e("ERRORDOWNLOADING", errorMessage);
            }
        });
    }

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
}
