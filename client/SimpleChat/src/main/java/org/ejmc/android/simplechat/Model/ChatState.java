package org.ejmc.android.simplechat.Model;

import android.os.Handler;
import android.os.Message;
import org.ejmc.android.simplechat.SimpleChat;
import org.ejmc.android.simplechat.View.ChatView;
import org.ejmc.android.simplechat.View.IChatView;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: psm1984
 * Date: 2/12/13
 * Time: 9:23
 */
public class ChatState {
    private static ChatState chatState = null;
    private Vector<ChatMessage> messagesInChatSession;

    private ChatState() {
        messagesInChatSession = new Vector<ChatMessage>();
    }

    public static ChatState getChatState(){
        if (chatState==null) chatState = new ChatState();
        return chatState;
    }

    public Vector<ChatMessage> getMessages(){
        return messagesInChatSession;
    }

    public void login() {
        messagesInChatSession = new Vector<ChatMessage>();
    }

    synchronized public void addMessages(Vector<ChatMessage> messagesToAdd)  {
        for (ChatMessage chatMessage : messagesToAdd) {
            if (this.messagesInChatSession.size() > 0) {
                ChatMessage lastChatMessage = this.messagesInChatSession.lastElement();
                if (chatMessage.nick.equals(lastChatMessage.nick)) lastChatMessage.message += "\n" + chatMessage.message;
                else this.messagesInChatSession.add(chatMessage);
            } else {
                this.messagesInChatSession.add(chatMessage);
            }
        }
    }

}
