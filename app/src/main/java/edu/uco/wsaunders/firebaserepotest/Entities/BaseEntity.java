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

    /**
     * saveChanges saves the changes from the specified value to
     * a record identified by the provided key
     * @param key identifies the record key
     * @param value indicates the value of the record
     * @param <T> identifies the type of the value object
     */
    protected <T> void saveChanges(String key, T value) {
        DatabaseReference db = getDataContext();
        db.child(key.toString()).setValue(value);
    }
}
