package edu.uco.wsaunders.firebaserepotest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

import edu.uco.wsaunders.firebaserepotest.Authentication.UserAuthentication;
import edu.uco.wsaunders.firebaserepotest.Entities.User;
import edu.uco.wsaunders.firebaserepotest.Entities.Users;
import edu.uco.wsaunders.firebaserepotest.Interfaces.AuthCompleteListener;
import edu.uco.wsaunders.firebaserepotest.Interfaces.QueryCompleteListener;

public class MainActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button loginButton;

    UserAuthentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


 /*       addRecord = (Button)findViewById(R.id.addRecord);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Users users = new Users();


                //Add a new user
                //User newUser = new User();
                //newUser.setName("Frank");
                //users.add(newUser);

                //Update an existing user
                //newUser.setName("Doug");
                //users.update(newUser);

                //Find an existing user
                //User foundUser = users.find("Doug");

                //Update user
                //foundUser.setName("Franken");
                //users.update(foundUser);

                users.find(Arrays.asList("name"),
                        Arrays.asList("Frank"),
                        new QueryCompleteListener<User>() {
                            @Override
                            public void onQueryComplete(ArrayList<User> entities) {
                                for (User entity : entities ) {
                                    Log.d("### removing ###", entity.toString());
                                    //users.remove(entity);
                                }
                            }
                        });
            }
        });*/
    }

    private void initialize() {
        auth = new UserAuthentication(this);

        findControlReferences();
        setControlEvents();
    }

    private void findControlReferences() {
        userName = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
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
                            }
                        });
            }
        });
    }
}
