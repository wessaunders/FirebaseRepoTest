package edu.uco.wsaunders.firebaserepotest.Interfaces;

import java.util.ArrayList;
import java.util.List;

import edu.uco.wsaunders.firebaserepotest.Entities.Entity;

/**
 * Repository interface
 */

public interface Repository<T extends Entity> {
    void add(T entity);
    void add(ArrayList<T> entities);
    void update(T entity);
    void remove(T entity);
    void remove(ArrayList<T> entities);
    void find(List<String> searchFields, List<String> searchValues, QueryCompleteListener<T> onQueryComplete);
}
