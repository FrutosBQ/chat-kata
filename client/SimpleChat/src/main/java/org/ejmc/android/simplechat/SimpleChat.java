package org.ejmc.android.simplechat;

import android.app.Application;
import android.content.Context;


public class SimpleChat extends Application {
        private static Context context;

        public void onCreate(){
            super.onCreate();
            SimpleChat.context = getApplicationContext();
        }

        public static Context getAppContext() {
            return SimpleChat.context;
        }

}
