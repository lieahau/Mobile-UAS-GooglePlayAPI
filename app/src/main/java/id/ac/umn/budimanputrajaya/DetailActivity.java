package id.ac.umn.budimanputrajaya;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // START GET DATA
        Intent intent = getIntent();
        final POJOApplication appData = intent.getParcelableExtra("Application Data");
        final int position = intent.getIntExtra("Position", 999);
        // END GET DATA

        // START SETUP TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(appData.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        // END SETUP TOOLBAR

        // START INITIAL
        ImageView icon = findViewById(R.id.icon);
        TextView name = findViewById(R.id.name);
        TextView publisherName = findViewById(R.id.publishername);
        TextView version = findViewById(R.id.version);
        TextView rating = findViewById(R.id.rating);
        TextView price = findViewById(R.id.price);
        TextView downloads = findViewById(R.id.downloads);
        TextView description = findViewById(R.id.description);
        Button btnInstall = findViewById(R.id.btn_install);
        FloatingActionButton fabRegister = findViewById(R.id.fab_register);
        // END INITIAL

        // START SET DATA
        Picasso.get()
                .load(appData.getIconUrl())
                .error(android.R.drawable.alert_dark_frame)
                .placeholder(android.R.drawable.alert_dark_frame)
                .into(icon);

        name.setText(appData.getName());
        publisherName.setText(appData.getPublisherName());

        StringBuilder ver = new StringBuilder();
        ver.append(getText(R.string.v));
        ver.append(appData.getVersion());
        version.setText(ver);

        rating.setText(appData.getRating());
        price.setText(appData.getPrice());
        downloads.setText(appData.getDownloads());
        description.setText(appData.getDescription());

        publisherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appData.getPublisherUrl()));
                startActivity(intent);
            }
        });

        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appData.getStoreUrl()));
                startActivity(intent);
            }
        });

        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "Success register " + appData.getName(), Toast.LENGTH_LONG).show();

                NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                notificationHelper.createNotification(appData.getName(), appData.getIconUrl(), position);
            }
        });
        // END SET DATA
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
