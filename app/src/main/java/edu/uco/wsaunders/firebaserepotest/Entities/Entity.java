package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * BaseEntity provides the application logic used by all entities for database access
 */
public abstract class Entity {
    /**
     * saveChanges saves the changes from the specified value to
     * a record identified by the provided key
     * @param key identifies the record key
     * @param value indicates the value of the record
     * @param <T> identifies the type of the value object
     */
    /*protected <T> void saveChanges(String key, T value) {
        DatabaseReference db = getDataContext();
        db.child(key.toString()).setValue(value);
    }*/

    private UUID key;

    /**
     * Default constructor
     */
    public Entity() {
        key = UUID.randomUUID();
    }

    @Exclude
    public String getKey() {
        return key.toString();
    }

    @Exclude
    protected void setKey(UUID key) {
        this.key = key;
    }
}