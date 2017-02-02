package edu.uco.wsaunders.firebaserepotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * RecordActivity
 */

public class RecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize();


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
}
