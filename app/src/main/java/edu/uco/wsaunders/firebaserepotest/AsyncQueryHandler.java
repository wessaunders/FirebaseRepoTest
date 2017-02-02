package edu.uco.wsaunders.firebaserepotest;

import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.uco.wsaunders.firebaserepotest.Entities.User;
import edu.uco.wsaunders.firebaserepotest.Entities.Users;

/**
 * AsyncQueryHandler
 */

public class AsyncQueryHandler extends AsyncTask<Map<String, Users>, Void, ArrayList<User>> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ArrayList<User> doInBackground(Map<String, Users>... params) {
        ArrayList<User> users = new ArrayList<User>();
        Object[] keys = params[0].keySet().toArray();

        Task<User> userTask = Tasks
                .call(new GetUserCallable1(keys[0].toString()));

        try {
            User foundUser = Tasks.await(userTask);
            users.add(foundUser);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        super.onPostExecute(users);
    }
}
