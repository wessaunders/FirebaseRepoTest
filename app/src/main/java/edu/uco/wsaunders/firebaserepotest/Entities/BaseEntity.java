package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

/**
 * BaseEntity provides the application logic used by all entities for database access
 */
public abstract class BaseEntity {
    @Exclude
    protected DatabaseReference getDataContext() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
