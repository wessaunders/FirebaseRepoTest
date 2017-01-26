package edu.uco.wsaunders.firebaserepotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.uco.wsaunders.firebaserepotest.Entities.User;

public class MainActivity extends AppCompatActivity {
    private Button addRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecord = (Button)findViewById(R.id.addRecord);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();
                newUser.setName("Scott");

                newUser.saveChanges();
                //db.child(newUser.getKey()).setValue(newUser);
            }
        });
    }
}
