package edu.uco.wsaunders.firebaserepotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Arrays;

import edu.uco.wsaunders.firebaserepotest.Entities.User;
import edu.uco.wsaunders.firebaserepotest.Entities.Users;
import edu.uco.wsaunders.firebaserepotest.Interfaces.QueryCompleteListener;

public class MainActivity extends AppCompatActivity {
    private Button addRecord;
    QueryCompleteListener<User> queryCompleteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecord = (Button)findViewById(R.id.addRecord);
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
                            public void onQueryComplete(User entity) {
                                Log.d("### removing ###", entity.toString());
                                //users.remove(entity);
                            }
                        });
            }
        });
    }
}
