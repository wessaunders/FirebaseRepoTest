package edu.uco.wsaunders.firebaserepotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import edu.uco.wsaunders.firebaserepotest.Facades.UserAuthenticationFacade;
import edu.uco.wsaunders.firebaserepotest.Interfaces.AuthCompleteListener;

public class CreateUserActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button createUserButton;

    UserAuthenticationFacade auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        initialize();
    }

    private void initialize() {
        auth = new UserAuthenticationFacade(this);

        findControlReferences();
        setControlEvents();
    }

    private void findControlReferences() {
        userName = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        createUserButton = (Button)findViewById(R.id.createUser);
    }

    private void setControlEvents()
    {
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createNewUser(
                        userName.getText().toString(),
                        password.getText().toString(),
                        new AuthCompleteListener() {
                            @Override
                            public void onAuthCompleted(boolean isSuccessful, FirebaseUser user) {
                                if (!isSuccessful) {
                                    Toast.makeText(getApplicationContext(), "Unable to create user.", Toast.LENGTH_LONG).show();
                                } else {
                                    finish();
                                }
                            }
                        }
                );
            }
        });
    }
}
