package edu.uco.wsaunders.firebaserepotest.Interfaces;

import com.google.firebase.auth.FirebaseUser;

import java.util.EventListener;

/**
 * AuthCompleteListener
 */

public interface AuthCompleteListener extends EventListener {
    void onAuthCompleted(boolean isSuccessful, FirebaseUser user);
}
