package org.ejmc.android.simplechat.Presenter;

import android.os.AsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: psm1984
 * Date: 28/11/13
 * Time: 17:37
 */
public class HttpPostAsynTask extends AsyncTask<String, Void, Boolean> {

    private final ChatPresenter cp;

    public HttpPostAsynTask(ChatPresenter cp) {
        this.cp = cp;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return cp.sendMessageToModel(params[0]);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        cp.sendMessageResult(result);
    }
}
