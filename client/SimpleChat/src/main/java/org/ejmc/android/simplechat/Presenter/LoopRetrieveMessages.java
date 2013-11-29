package org.ejmc.android.simplechat.Presenter;


import android.os.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 29/11/13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class LoopRetrieveMessages implements Runnable {
    private ChatPresenter cp;
    private Handler delayhandler;
    private boolean running = true;
    public LoopRetrieveMessages(ChatPresenter cp, Handler delayhandler) {
        this.cp = cp;
        this.delayhandler = delayhandler;
    }

    public void run() {
        if(running){
            HttpAsynTask task = new HttpAsynTask(cp);
            task.execute();
            delayhandler.postDelayed(this, 500);
        }
    }

    public void setPresenter(ChatPresenter presenter) {
        this.cp = presenter;
    }

    public void stop() {
        //To change body of created methods use File | Settings | File Templates.
        running= false;
    }
}
