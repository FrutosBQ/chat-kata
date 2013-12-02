package org.ejmc.android.simplechat.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class ChatMessage {

    @SerializedName("message")
    public  String message;

    @SerializedName("nick")
    public String nick;


    public ChatMessage(String user, String message_text) {
        //To change body of created methods use File | Settings | File Templates.
        this.message = message_text;
        this.nick = user;
    }


    /**
     * Define equality of state.
     */
    @Override public boolean equals(Object aThat) {
        if (this == aThat) return true;
        if (!(aThat instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage)aThat;

        return ( this.nick.equals(that.nick) && ( this.message.equals(that.message)));
    }
}
