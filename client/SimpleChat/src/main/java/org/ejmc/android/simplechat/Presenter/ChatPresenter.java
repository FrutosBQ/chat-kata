package org.ejmc.android.simplechat.Presenter;

import org.ejmc.android.simplechat.Model.Message;
import org.ejmc.android.simplechat.Model.ParseNetResultException;
import org.ejmc.android.simplechat.Model.ServerComunicationModel;
import org.ejmc.android.simplechat.View.IChatView;

import java.io.IOException;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class ChatPresenter implements IChatPresenter{
    IChatView view;

    private ServerComunicationModel scm;

    public ChatPresenter(){
        scm = new ServerComunicationModel("http://172.16.100.188:8080");
    }

    @Override
    public void setView(IChatView chatView) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.view = chatView;
    }

    @Override
    public void startReadingMessageProcess() {
            HttpAsynTask task = new HttpAsynTask(this);
            task.execute();

    }

    public void updateView(Vector<Message> last_messages){
         view.newMessages(last_messages);
    }

    @Override
    public void sendMessage(String message) {
        try {
            scm.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void setScm(ServerComunicationModel scm) {
        this.scm = scm;
    }

    public Vector<Message> retriveMessages() throws IOException, ParseNetResultException {
        return scm.getLastMessages();
    }
}
