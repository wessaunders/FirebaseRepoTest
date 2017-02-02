package edu.uco.wsaunders.firebaserepotest.Entities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import edu.uco.wsaunders.firebaserepotest.AsyncQueryHandler;
import edu.uco.wsaunders.firebaserepotest.Interfaces.QueryCompleteListener;
import edu.uco.wsaunders.firebaserepotest.Interfaces.Repository;

/**
 * Users
 */

public class Users<T extends Entity> implements Repository<User> {
    private DatabaseReference dataContext;
    private String identifier;

    public Users() {
    }

    @Override
    public void add(User entity) {
        dataContext = FirebaseDatabase.getInstance().getReference(entity.getClass().getSimpleName());
        dataContext.child(entity.getKey().toString()).setValue(entity);
    }

    @Override
    public void add(ArrayList<User> entities) {
        //T entityFromDatabase = dataContext.child(entity.getKey().toString());
        dataContext = FirebaseDatabase.getInstance().getReference(entities.get(0).getClass().getSimpleName());
        for (User entity : entities)
        {
            dataContext.child(entity.getKey().toString()).setValue(entity);
        }
    }

    @Override
    public void update(User entity) {
        //T entityFromDatabase = dataContext.child(entity.getKey().toString());
        dataContext = FirebaseDatabase.getInstance().getReference(entity.getClass().getSimpleName());
        dataContext.child(entity.getKey().toString()).setValue(entity);
    }

    @Override
    public void remove(User entity) {
        dataContext = FirebaseDatabase.getInstance().getReference(entity.getClass().getSimpleName());
        dataContext.child(entity.getKey().toString()).removeValue();
    }

    @Override
    public void remove(ArrayList<User> entities) {
        dataContext = FirebaseDatabase.getInstance().getReference(entities.get(0).getClass().getSimpleName());
        for (User entity : entities) {
            dataContext.child(entity.getKey().toString()).removeValue();
        }
    }

    @Override
    public void find(List<String> searchFields, List<String> searchValues, QueryCompleteListener<User> onQueryComplete) {
        DatabaseReference dataContext = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        Query query = dataContext.orderByChild(searchFields.get(0)).equalTo(searchValues.get(0));

        final QueryCompleteListener<User> finalQueryCompleteListener = onQueryComplete;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User foundUser = null;

                for (DataSnapshot record : dataSnapshot.getChildren()) {
                    Log.d("*** key ***", record.getKey());
                    UUID recordKey = UUID.fromString(record.getKey());

                    foundUser = record.getValue(User.class);
                    foundUser.setKey(recordKey);

                    Log.d("*** founduser ***", foundUser.toString());
                }

                finalQueryCompleteListener.onQueryComplete(foundUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
//                Log.w("Users", "Users.find:onCancelled", databaseError.toException());
            }
        });
    }













    public Query query(String identifier) {
        DatabaseReference dataContext = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        Query query = dataContext.orderByChild("name").equalTo(identifier);
        return query;
    }


    public User find1(String identifier) {
        HashMap<String, Users> params = new HashMap<String, Users>();
        params.put(identifier, this);

        final ArrayList<User> foundUsers = new ArrayList<User>();

        AsyncQueryHandler asyncHandler = new AsyncQueryHandler();
        AsyncTask<Map<String, Users>, Void, ArrayList<User>> asyncTask = asyncHandler.execute(params);

        try {
            ArrayList<User> returnedUsers = asyncTask.get();
            foundUsers.addAll(returnedUsers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return search(identifier, foundUsers);
        //Executor executor = Executors.newSingleThreadExecutor();
        //Task<User> userTask = Tasks
        //        .call(executor, new GetUserCallable(identifier));

        //User foundUser = null;
        //foundUser = userTask.getResult();
    }

    private User search(final String identifier, final ArrayList<User> users)
    {
        new Thread(new Runnable() {

            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                Task<User> userTask = Tasks
                        .call(new GetUserCallable(identifier));

                try {
                    User foundUser = Tasks.await(userTask);
                    users.add(foundUser);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return users.get(0);
    }

    class GetUserCallable implements Callable<User> {
        String identifier;

        public GetUserCallable(String identifier) {
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


    class GetUser implements Continuation<User, Task<User>> {
        @Override
        public Task<User> then(@NonNull Task<User> task) throws Exception {
            final TaskCompletionSource<User> tcs = new TaskCompletionSource();

            DatabaseReference dataContext = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
            Query query = dataContext.orderByChild("name").equalTo(identifier);

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

                        User foundRecord = record.getValue(User.class);
                        foundRecord.setKey(recordKey);

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
}}
