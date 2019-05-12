package id.ac.umn.budimanputrajaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private CredentialDbHelper dbHelper;

    private EditText inputUsername, inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar

        // START PREPARING DATABASE
        dbHelper = new CredentialDbHelper(getApplicationContext());
        // END PREPARING DATABASE

        // START LOGIN
        inputUsername = findViewById(R.id.inputUsername);
        inputPass = findViewById(R.id.inputPass);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.isUserExist(inputUsername.getText().toString(), inputPass.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, DataActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // END LOGIN
    }

}
