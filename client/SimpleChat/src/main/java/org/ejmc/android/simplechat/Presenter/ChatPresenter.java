package org.ejmc.android.simplechat.Presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.ejmc.android.simplechat.Model.ChatState;
import org.ejmc.android.simplechat.Model.ChatMessage;
import org.ejmc.android.simplechat.Model.ParseNetResultException;
import org.ejmc.android.simplechat.Model.ServerComunicationModel;
import org.ejmc.android.simplechat.SimpleChat;
import org.ejmc.android.simplechat.View.IChatView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 16:56
 */
public class ChatPresenter implements IChatPresenter {
    IChatView view;
    private ServerComunicationModel scm;
    private ChatState chatState;
    private static Timer timer = null;

    public ChatPresenter(String username) {
<<<<<<< HEAD
        scm = new ServerComunicationModel("http://172.16.100.43:8080", username);

=======
        scm       = new ServerComunicationModel("http://172.16.100.43:8080", username);
        chatState = ChatState.getChatState();
>>>>>>> MessageRefactor
    }

    @Override
    public void setView(IChatView chatView) {
        this.view = chatView;
    }

    @Override
    public void startReadingMessageProcess() {
        TimerTask tt = new TimerTask(){
            public void run(){
                try {
                    Vector<ChatMessage> chatMessages = scm.getLastMessages();
                    if (chatMessages.size() > 0) {
                        chatState.addMessages(chatMessages);
                        view.updateMessages();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseNetResultException e) {
                    e.printStackTrace();
                }
            }
        };
        if (timer==null) {
            timer = new Timer();
            timer.schedule(tt,500,500);
        }
    }

    public boolean sendMessageToModel(String message) {
        try {
            return scm.sendMessage(message);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void sendMessage(String message) {
        HttpPostAsynTask task = new HttpPostAsynTask(this);
        task.execute(message);
    }

    public void setScm(ServerComunicationModel scm) {
        this.scm = scm;
    }

    public void sendMessageResult(boolean result) {
        if (result) view.messageSendedOK();
        else view.messageSendedError();
    }
}

