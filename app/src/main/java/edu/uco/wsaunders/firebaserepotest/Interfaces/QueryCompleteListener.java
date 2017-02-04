package edu.uco.wsaunders.firebaserepotest.Interfaces;

import java.util.ArrayList;
import java.util.EventListener;

import edu.uco.wsaunders.firebaserepotest.Entities.Entity;

/**
 * QueryCompleteListener defines the interface for the event that
 * fires when queries are completed
 */

public interface QueryCompleteListener<T extends Entity> extends EventListener {
    void onQueryComplete(ArrayList<T> entities);
}
