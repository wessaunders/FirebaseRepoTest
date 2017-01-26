package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

/**
 * User class defines the attributes for a User
 */

@IgnoreExtraProperties
public class User extends BaseEntity {
    private String name;
    private final UUID key;

    /**
     * Default constructor
     */
    public User() {
        key = UUID.randomUUID();
    }

    public String getKey() {
        return key.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveChanges() {
        DatabaseReference db = getDataContext();
        db.child(key.toString()).setValue(this);
    }
}
