package edu.uco.wsaunders.firebaserepotest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Callable;

import edu.uco.wsaunders.firebaserepotest.Entities.User;

/**
 * GetUserCallable
 */

public class GetUserCallable1 implements Callable<User> {
    String identifier;

    public GetUserCallable1(String identifier) {
        this.identifier = identifier;
    }
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public User call() throws Exception {
        DatabaseReference dataContext = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        Query query = dataContext.orderByChild("name").equalTo(identifier);
        final ArrayList<User> usersList = new ArrayList<User>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot record : dataSnapshot.getChildren()) {
                        /*
                        Map<String, Object> foundObject = (Map<String, Object>) user.getValue();
                        Log.d("*** foundobject ***", foundObject.toString());
                        String returnedKey = user.getKey();
                        Log.d("*** key ***", returnedKey);
                        UUID key = UUID.fromString(user.getKey());
                        Log.d("*** key ***", key.toString());
                        foundUser = new User(key);
                        foundUser.setName(foundObject.get("name").toString());
                        */

                    Log.d("*** key ***", record.getKey());
                    UUID recordKey = UUID.fromString(record.getKey());

                    User foundUser = record.getValue(User.class);
                    foundUser.setKey(recordKey);

                    Log.d("*** founduser ***", foundUser.toString());
                    usersList.add(foundUser);
                    Log.d("*** userlist ***", String.valueOf(usersList.size()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //tcs.setException(databaseError.toException());
            }
        });

        Log.d("*** returning ***", usersList.get(0).toString());
        return usersList.get(0);
    }
}
