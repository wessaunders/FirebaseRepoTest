package edu.uco.wsaunders.firebaserepotest;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

import edu.uco.wsaunders.firebaserepotest.Entities.Entity;
import edu.uco.wsaunders.firebaserepotest.Entities.User;

/**
 * QueryManager
 */

public class QueryManager<T extends Entity> implements Continuation<Void, Task<T>> {
    Class<T> classRef;
    String searchTerm;

    public QueryManager(Class<T> classRef, String searchTerm) {
        this.classRef = classRef;
        this.searchTerm = searchTerm;
    }

    @Override
    public Task<T> then(@NonNull Task<Void> task) throws Exception {
        final TaskCompletionSource<T> tcs = new TaskCompletionSource();

        DatabaseReference dataContext = FirebaseDatabase.getInstance().getReference(classRef.getSimpleName());
        Query query = dataContext.orderByChild("name").equalTo(searchTerm);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot record : dataSnapshot.getChildren())
                {
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

                    T foundRecord = record.getValue(classRef);
                    //foundRecord.setKey(recordKey);

                    tcs.setResult(foundRecord);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });

        return tcs.getTask();
    }
}
