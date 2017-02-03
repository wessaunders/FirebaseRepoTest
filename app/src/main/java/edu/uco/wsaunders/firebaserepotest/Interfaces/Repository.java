package edu.uco.wsaunders.firebaserepotest.Interfaces;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import edu.uco.wsaunders.firebaserepotest.Entities.Entity;
import edu.uco.wsaunders.firebaserepotest.Entities.User;

/**
 * Repository defines the interface for all repository objects
 */

public interface Repository<T extends Entity> {
    void add(T entity);
    void add(ArrayList<T> entities);
    void update(T entity);
    void remove(T entity);
    void remove(ArrayList<T> entities);
    void find(List<String> searchFields, List<String> searchValues, QueryCompleteListener<T> onQueryComplete);
    Task<ArrayList<T>> find(List<String> searchFields, List<String> searchValues);
}
