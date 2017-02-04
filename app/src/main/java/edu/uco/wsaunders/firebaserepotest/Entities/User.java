package edu.uco.wsaunders.firebaserepotest.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

/**
 * User class defines the attributes for a User
 */

@IgnoreExtraProperties
public class User extends Entity {
    private String name;

    /**
     * Default constructor
     */
    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public void setKey(UUID key) {
        super.setKey(key);
    }
}
