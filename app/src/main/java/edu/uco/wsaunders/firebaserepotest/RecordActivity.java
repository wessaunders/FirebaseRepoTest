package edu.uco.wsaunders.firebaserepotest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.Arrays;

import edu.uco.wsaunders.firebaserepotest.Entities.User;
import edu.uco.wsaunders.firebaserepotest.Entities.Users;
import edu.uco.wsaunders.firebaserepotest.Interfaces.QueryCompleteListener;

public class RecordActivity extends AppCompatActivity {
    private EditText search;
    private EditText foundUser;
    private Button findUserButton;
    private Button addUserButton;
    private Button removeUserButton;
    private Button findAndRemoveButton;

    private Users<User> usersRepository;
    private User findResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        usersRepository = new Users<>();

        initialize();
    }

    private void initialize() {
        findControlReferences();
        setControlEvents();
    }

    private void findControlReferences() {
        search = (EditText)findViewById(R.id.search);
        foundUser = (EditText)findViewById(R.id.foundUser);
        findUserButton = (Button)findViewById(R.id.findUser);
        addUserButton = (Button)findViewById(R.id.addUser);
        removeUserButton = (Button)findViewById(R.id.removeUser);
        findAndRemoveButton = (Button)findViewById(R.id.findAndRemove);
    }

    private void setControlEvents()
    {
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(search.getText().toString());
            }
        });

        findUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findUser(search.getText().toString());
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser(search.getText().toString());
            }
        });

        findAndRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAndRemoveUser(search.getText().toString());
            }
        });
    }

    /**
     * addUser adds a new user
     */
    private void addUser(String search) {
        User newUser = new User();
        newUser.setName(search);
        usersRepository.add(newUser);
    }

    /**
     * findUser finds the user matching the given name
     */
    private void findUser(String search) {
        usersRepository.find(
            Arrays.asList("name"),
            Arrays.asList(search),
                    new QueryCompleteListener<User>() {
                        @Override
                        public void onQueryComplete(ArrayList<User> entities) {
                            for (User entity : entities) {
                                foundUser.setText(entity.getName());
                                findResult = entity;
                            }
                        }
                    }
            );
    }

    /**
     * removeUser removes the specified user
     */
    private void removeUser(String search) {
        usersRepository.find(
                Arrays.asList("name"),
                Arrays.asList(search),
                new QueryCompleteListener<User>() {
                    @Override
                    public void onQueryComplete(ArrayList<User> entities) {
                        for (User entity : entities) {
                            usersRepository.remove(entity);
                        }
                    }
                }
        );
    }

    /**
     * findAndRemove user finds and then removes the specified user by calling
     * the chained find method and then continuing on to the remove method
     *
     * @param search
     */
    private void findAndRemoveUser(String search) {
        Task<ArrayList<User>> findUsersTask = usersRepository.find(Arrays.asList("name"), Arrays.asList(search));
        findUsersTask.continueWith(new Continuation<ArrayList<User>, Task<ArrayList<User>>>() {
            @Override
            public Task<ArrayList<User>> then(@NonNull Task<ArrayList<User>> task) throws Exception {
                TaskCompletionSource<ArrayList<User>> taskCompletionSource = new TaskCompletionSource<ArrayList<User>>();

                ArrayList<User> foundUsers = task.getResult();
                for (User removeUser : foundUsers) {
                    usersRepository.remove(removeUser);
                }

                return taskCompletionSource.getTask();
            }
        });
    }
}
