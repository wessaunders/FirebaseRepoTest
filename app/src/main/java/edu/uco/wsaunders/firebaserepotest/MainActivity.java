package edu.uco.wsaunders.firebaserepotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import edu.uco.wsaunders.firebaserepotest.Facades.UserAuthenticationFacade;
import edu.uco.wsaunders.firebaserepotest.Interfaces.AuthCompleteListener;

public class MainActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button loginButton;
    private Button createUserButton;
    private Button recordsButton;

    UserAuthenticationFacade auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        auth = new UserAuthenticationFacade(this);

        findControlReferences();
        setControlEvents();
    }

    private void findControlReferences() {
        userName = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        createUserButton = (Button)findViewById(R.id.createUser);
        recordsButton = (Button)findViewById(R.id.records);
    }

    private void setControlEvents()
    {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.logIn(
                    userName.getText().toString(),
                    password.getText().toString(),
                    new AuthCompleteListener() {
                        @Override
                        public void onAuthCompleted(boolean isSuccessful, FirebaseUser user) {
                           Log.d("MAIN", String.valueOf(isSuccessful));

                            if (isSuccessful) {
                                Toast.makeText(getApplicationContext(), user.getEmail() + " was logged in successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Unable to log in.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUserIntent = new Intent(getApplicationContext(), CreateUserActivity.class);
                startActivity(createUserIntent);
            }
        });

        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordIntent = new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(recordIntent);
            }
        });
    }
}
