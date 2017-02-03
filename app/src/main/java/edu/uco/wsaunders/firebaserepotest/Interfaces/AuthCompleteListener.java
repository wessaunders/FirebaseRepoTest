package edu.uco.wsaunders.firebaserepotest.Interfaces;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * AuthCompleteListener
 */

public interface AuthCompleteListener extends EventListener {
    public void onAuthCompleted(boolean isSuccessful, FirebaseUser user);
}
