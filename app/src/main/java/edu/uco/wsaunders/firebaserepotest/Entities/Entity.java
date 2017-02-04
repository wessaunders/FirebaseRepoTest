package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * BaseEntity provides the application logic used by all entities for database access
 */
public abstract class Entity {
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
