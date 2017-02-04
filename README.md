# FirebaseRepoTest
This is a test project to provide abstractions/facades around Firebase operations in Android.

*All of the examples of firebase code are based on the code samples from the excellent [https://github.com/firebase/quickstart-android] (Firebase Quickstart SDK).  If you haven't checked it out I highly encourage it.*

#####Disclaimer: This is a demo
This is provided as an example, and as such it does not implement all of the firebase features that are available.  It is simply intended to be an example project.  
And yes, the UI is terrible.  Again, demo project....

###Authentication
Firebase authentication is implemented through a facade that wraps around the firebase authentication library.  The facade provides automatic setup and teardown of all associated authentication event handlers, so that all that is required for a developer is to simply declare the authentication facade and call the appropriate method.

####Examples
- Authentication using email address via regular firebase code
This example essentially gets an instance of the databse, creates an AuthStateListener, creates an onCreate event handler for listening to the responses from the AuthStatelistener, and then has to add/remove the event handler as needed.
Then after all the setup, it signs in with the provided credentials by calling signInWithEmailAndPassword
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

- Authentication using email address via abstraction
Simply create the abstraction and call the logIn method.  The logIn method requires an email, password, and an instance of an AuthCompleteListener which will contain the results of the authentication, and if successful, a reference to the FirebaseUser that was logged in.

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
- Adding a record
  - Firebase
    ```
    User newUser = new User();
    newUser.setName("Hello, World!");

    private DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference();
    mDatabase.child("users").child(userId).setValue(newUser);
    ```
  - Repository abstraction
    ```
    User newUser = new User();
    newUser.setName("Hello, World!");

    Users<User> usersRepository = usersRepository = new Users<>();
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
    Users<User> usersRepository = usersRepository = new Users<>();
    
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
    Users<User> usersRepository = usersRepository = new Users<>();
    
    //assuming that users is the reference to the existing list of records to be removed
    usersRepository.remove(users);
    ```
    
- Finding a record
- Finding a record and then doing something else with it
