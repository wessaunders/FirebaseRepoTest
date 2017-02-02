package edu.uco.wsaunders.firebaserepotest.Interfaces;

import java.util.EventListener;

import edu.uco.wsaunders.firebaserepotest.Entities.Entity;

/**
 * QueryCompleteListener
 */

public interface QueryCompleteListener<T extends Entity> extends EventListener {
    public void onQueryComplete(T entity);
}
