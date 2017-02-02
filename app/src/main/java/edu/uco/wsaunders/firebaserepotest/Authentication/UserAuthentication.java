    package edu.uco.wsaunders.firebaserepotest.Authentication;

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
 * UserAuthentication
 */

public class UserAuthentication {
    private FirebaseAuth firebaseAuthenticator;
    private FirebaseAuth.AuthStateListener authenticatorListener;
    private Context context;

    public UserAuthentication(Context context) {
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
     * logIn
     * @param user
     * @param password
     * @param authCompleteListener
     */
    public void logIn(String user, String password, final AuthCompleteListener authCompleteListener) {
        Executor executor = Executors.newSingleThreadExecutor();
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
}
