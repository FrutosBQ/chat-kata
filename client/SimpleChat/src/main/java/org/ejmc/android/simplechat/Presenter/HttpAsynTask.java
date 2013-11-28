package org.ejmc.android.simplechat.Presenter;

import android.os.AsyncTask;
import org.ejmc.android.simplechat.Model.Message;
import org.ejmc.android.simplechat.Model.ParseNetResultException;

import java.io.IOException;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
class HttpAsynTask extends AsyncTask<String, Void, Vector<Message>> {

    //private final ServerComunicationModel scm;
    private final ChatPresenter cp;

    public  HttpAsynTask(ChatPresenter cp){
        this.cp = cp;
    }

    @Override
    protected Vector<Message> doInBackground(String... params) {
        try {
            return cp.retriveMessages();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseNetResultException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //view.newMessages(last_messages);
         return null;
    }



    @Override
    protected void onPostExecute(Vector<Message> result) {
                 cp.updateView(result);
    }
}