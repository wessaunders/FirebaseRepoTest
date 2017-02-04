# FirebaseRepoTest
This is a test project to provide abstractions/facades around Firebase operations in Android.

*All of the examples of firebase code are based on the code samples from the excellent [https://github.com/firebase/quickstart-android] (Firebase Quickstart SDK).  If you haven't checked it out I highly encourage it.*

###Authentication
Firebase authentication is implemented through a facade that wraps around the firebase authentication library.  The facade provides automatic setup and teardown of all associated authentication event handlers, so that all that is required for a developer is to simply declare the authentication facade and call the appropriate method.

####Examples
- Authentication 
  - **Firebase**
  
    This is an example of using email address to sign in with regular firebase code.  The example essentially gets an instance of the       databse, creates an AuthStateListener, creates an onCreate event handler for listening to the responses from the AuthStatelistener,     and then has to add/remove the event handler as needed.
    Then after all the setup, it signs in with the provided credentials by calling signInWithEmailAndPassword.
    ```
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Get an instance of the database
    mAuth = FirebaseAuth.getInstance();

    //Declare the onCreate event handler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...
    }

    //Add the auth state listener when the activity is started
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //Remove the auth state listener when the activity is stopped
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //Finally, sign in
    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                        Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });
    ```
  - **Abstraction**
  
    This example demonstrates using firebase authentication using email address via the firebase authentication facade. Simply create the abstraction and call the logIn method.  The logIn method requires an email, password, and an instance of an AuthCompleteListener which will contain the results of the authentication, and if successful, a reference to the FirebaseUser that was logged in.

    Adding/removing of the firebase event handlers is automatically taken care of.
    ```
    private UserAuthenticationFacade auth;

    //Initialize the facade and pass the current activity context
    auth = new UserAuthenticationFacade(this);

    //Log in
    auth.logIn(email, password, new AuthCompleteListener() {
        @Override
        public void onAuthCompleted(boolean isSuccessful, FirebaseUser user) {
            if (isSuccessful) {
                Log.w(TAG, user.getEmail() + " was logged in successfully.");
            } else {
                Log.w(TAG, "Unable to log in.");
            }
        }
    });
    ```
    
###Database access
Database access utilizes firebase's the built-in mechanism that automatically serializes/deserializes the relevant entities.
The database abstraction implements a repository interface, which offers and consistent and simplified syntax for firebase data access.

####Examples
- **Adding a record**
  - Firebase
    ```
    private User newUser = new User();
    newUser.setName("Hello, World!");

    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference();
    mDatabase.child("users").child(userId).setValue(newUser);
    ```
  - Repository abstraction
    ```
    private User newUser = new User();
    newUser.setName("Hello, World!");

    private Users<User> usersRepository = new Users<>();
    usersRepository.add(newUser);
  ```
  
- Removing a record

    The remove examples assume that the reference to the correct data to be removed has already been obtained.  An example of removing without an existing reference to the data is provided under the "Finding a record and then doing something else with it" example
    
  - Firebase
    ```
    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference();
    
    //assuming that user is the reference to the existing data
    mDatabase.child("users").removeValue(user);
    ```    
  - Repository abstraction
    ```
    private Users<User> usersRepository = usersRepository = new Users<>();
    
    //assuming that user is the reference to the existing data
    usersRepository.remove(user);
    ```
    
- Remove multiple records
  - Firebase
  
    Removing multiple records with the regular firebase code is basically the same as removing a single record, just iterate over the list of records to be removed.    
    ```
    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference();
    
    //assuming that users is the reference to the existing list of records to be removed
    for (User user : users)
    {
        mDatabase.child("users").removeValue(user);
    }
    ```
  - Repository abstraction
  
    Though not explicity implemented in the demo, the repository interface provided for a remove method that accepts an ArrayList of records.  An example would look something like this:    
    ```
    private Users<User> usersRepository = new Users<>();
    
    //assuming that users is the reference to the existing list of records to be removed
    usersRepository.remove(users);
    ```
    
- Finding a record

    Getting an existing record(s) in firebase requires getting a reference to the firebase database, defining a query, adding an event listener to the query, and then handling the results from the onDataChange method in the event listener
    
  - Firebase
    ```
    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference("name");
    
    //Could also get the database child reference by explicity specifying .child after the reference declaration
    //This is functionally equivalent to the shortcut above
    //mDatabase = FirebaseDatabase.getInstance().getReference().child("name");
  
    //Define the database query
    Query query = dataContext.orderByChild("name").equalTo("Justin Case");
    
    //Add an event listener to the query
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot record : dataSnapshot.getChildren()) {
                // ...
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Oh no, getting User failed, log a message
            // Log.w(LOG, "query cancelled", databaseError.toException());
            }
        });    
    ```
  - Repository abstraction
  
    This is where the advantages of the repository really being to become apparent.  To find a record, all that is required is to define a reference to the repository and then call the .find method.  The find method accepts a list of fields to search on, a list of values to match up to those fields, and a QueryCompleteListener that includes the results from the query.
    ```
    private Users<User> usersRepository = new Users<>();

    usersRepository.find(Arrays.asList("name"), Arrays.asList("Justin Case"), new QueryCompleteListener<User>() {
        @Override
        public void onQueryComplete(ArrayList<User> entities) {
            for (User entity : entities) {
                // ...
            }
        }
    });    
    ```
    
- Finding a record and then doing something else with it
  - Firebase
  
    Getting an existing record(s) in firebase requires getting a reference to the firebase database, defining a query, adding an event listener to the query, and then after performing the second action after retrieving the results in the onDataChange method in the event listener
    ```
    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference("name");
    
    //Could also get the database child reference by explicity specifying .child after the reference declaration
    //This is functionally equivalent to the shortcut above
    //mDatabase = FirebaseDatabase.getInstance().getReference().child("name");
  
    //Define the database query
    Query query = dataContext.orderByChild("name").equalTo("Justin Case");
    
    //Add an event listener to the query
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot record : dataSnapshot.getChildren()) {
                mDatabase.removeValue(record);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Oh no, getting User failed, log a message
            // Log.w(LOG, "query cancelled", databaseError.toException());
        }
    });    
    ```  
  - Repository abstraction
  
    The repository abstraction exposes a find method that can return a task, which can be chained using the methods from [https://developers.google.com/android/reference/com/google/android/gms/tasks/package-summary] (Google's Task API).  
    
    *Note that this provides the ability to chain multiple searches together, and that each successive search will operate based on the results from the previous task.  Check out the findAndRemoveUser example in the RecordActivity class for more information.*
    ```
    Users<User> usersRepository = new Users<>();
            
    Task<ArrayList<User>> findUsersTask = usersRepository.find(Arrays.asList("name"), Arrays.asList("Justin Case"));
        findUsersTask.continueWith(new Continuation<ArrayList<User>, Task<ArrayList<User>>>() {
            @Override
            public Task<ArrayList<User>> then(@NonNull Task<ArrayList<User>> task) throws Exception {
                TaskCompletionSource<ArrayList<User>> taskCompletionSource = new TaskCompletionSource<ArrayList<User>>();

                ArrayList<User> entities = task.getResult();
                for (User user : entities) {
                    usersRepository.remove(user);
                }

                return taskCompletionSource.getTask();
            }
        });
    ```

#####Disclaimer: This is a demo

This is provided as an example, and as such it does not implement all of the firebase features that are available.  It is simply intended to be an example project.  Also, if there is a better way to do some of these operations in firebase, I'd love to hear about it - any and all constructive comments are welcome!

And yes, the UI is terrible.  Again, demo project....

