package org.ejmc.android.simplechat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import org.ejmc.android.simplechat.Model.ChatMessage;

import java.util.Vector;


public class SimpleChat extends Application {
    private static Context context;
    public final static int MSG_NEW_MESSAGES = 0;

    public void onCreate() {
        super.onCreate();
        SimpleChat.context = getApplicationContext();
        registerActivityLifecycleCallbacks(new LifeCycleHandler());

    }

    public static Context getAppContext() {
        return SimpleChat.context;
    }


    private class LifeCycleHandler implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }
}

