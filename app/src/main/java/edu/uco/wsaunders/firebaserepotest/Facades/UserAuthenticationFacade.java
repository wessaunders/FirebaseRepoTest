    package edu.uco.wsaunders.firebaserepotest.Facades;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.uco.wsaunders.firebaserepotest.Interfaces.AuthCompleteListener;

/**
 * UserAuthenticationFacade wraps a facade layer around the firebase authentication provider
 * that provides easier access to the authentication logic
 */

public class UserAuthenticationFacade {
    private FirebaseAuth firebaseAuthenticator;
    private FirebaseAuth.AuthStateListener authenticatorListener;
    private Context context;

    /**
     * Default constructor
     * @param context indicates the context that the UserAuthenticationFacade is being
     *                referenced from
     */
    public UserAuthenticationFacade(Context context) {
        this.context = context;
        firebaseAuthenticator = FirebaseAuth.getInstance();

        authenticatorListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    /**
     * logIn logs in a user
     * @param user identifies the user
     * @param password indicates the user's password
     * @param authCompleteListener identifies the AuthCompleteListener to use when sending back the
     *                             results of the authentication
     */
    public void logIn(String user, String password, final AuthCompleteListener authCompleteListener) {
        firebaseAuthenticator.addAuthStateListener(authenticatorListener);
        firebaseAuthenticator
                .signInWithEmailAndPassword(user, password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser loggedInUser = null;
                        boolean success = task.isSuccessful();

                        if (success) {
                            loggedInUser = firebaseAuthenticator.getCurrentUser();
                        }

                        authCompleteListener.onAuthCompleted(success, loggedInUser);

                        if (authenticatorListener != null) {
                            firebaseAuthenticator.removeAuthStateListener(authenticatorListener);
                        }
                    }
                });
    }

    /**
     * logOutCurrentUser logs the current user out of the system
     */
    public void logOutCurrentUser() {
        firebaseAuthenticator.getInstance().signOut();
    }

    /**
     * createNewUser creates a new user account with the specified user account and password
     * information
     * @param user identifies the user
     * @param password indicates the user's password
     * @param authCompleteListener identifies the AuthCompleteListener to use when sending back the
     *                             results of the authentication
     */
    public void createNewUser(String user, String password, final AuthCompleteListener authCompleteListener) {
        firebaseAuthenticator.addAuthStateListener(authenticatorListener);
        firebaseAuthenticator
                .createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean success = task.isSuccessful();
                        authCompleteListener.onAuthCompleted(success, null);

                        if (authenticatorListener != null) {
                            firebaseAuthenticator.removeAuthStateListener(authenticatorListener);
                        }
                    }
                });
    }
}
