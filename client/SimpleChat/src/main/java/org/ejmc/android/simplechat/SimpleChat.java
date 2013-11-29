package org.ejmc.android.simplechat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import org.ejmc.android.simplechat.Model.Message;
import org.ejmc.android.simplechat.Presenter.ChatPresenter;
import org.ejmc.android.simplechat.Presenter.LoopRetrieveMessages;
import org.ejmc.android.simplechat.View.ChatView;

import java.util.Vector;


public class SimpleChat extends Application {
    private static Context context;
    private static LoopRetrieveMessages loopRetrieveMessages;
    private static Vector<Message> messages_backup;

    private static Handler delayhandler;

    public void onCreate(){
            super.onCreate();
            SimpleChat.context = getApplicationContext();
            delayhandler = new Handler();
        registerActivityLifecycleCallbacks(new LifeCycleHandler());

        }

        public static Context getAppContext() {
            return SimpleChat.context;
        }

    public static void startReadingMessages(ChatPresenter chatPresenter) {
        if (loopRetrieveMessages==null) {
            loopRetrieveMessages = new LoopRetrieveMessages(chatPresenter, delayhandler);

            delayhandler.postDelayed(loopRetrieveMessages, 500);
        }else{
            loopRetrieveMessages.setPresenter(chatPresenter);
        }
    }

    private class LifeCycleHandler implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onActivityStarted(Activity activity) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onActivityResumed(Activity activity) {
            //To change body of implemented methods use File | Settings | File Templates.
            if(activity.getClass().equals(ChatView.class)){
                ((ChatView)activity).startReadingMessageProcess();
                ((ChatView)activity).setMessage(messages_backup);

            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //To change body of implemented methods use File | Settings | File Templates.
            if(activity.getClass().equals(ChatView.class)){
                messages_backup = ((ChatView)activity).getMessage();
            }

            if(loopRetrieveMessages!=null){
                loopRetrieveMessages.stop();
                loopRetrieveMessages = null;
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}

