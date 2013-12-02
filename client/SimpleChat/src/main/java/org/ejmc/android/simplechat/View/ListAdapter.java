package org.ejmc.android.simplechat.View;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.ejmc.android.simplechat.Model.ChatMessage;
import org.ejmc.android.simplechat.Model.ChatState;
import org.ejmc.android.simplechat.R;
import org.ejmc.android.simplechat.SimpleChat;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: psm1984
 * Date: 2/12/13
 * Time: 10:35
 */
public class ListAdapter extends BaseAdapter {
    private Vector<ChatMessage> chatMessages;
    private String username;

    public ListAdapter(String username, Vector<ChatMessage> chatMessages) {
        this.username = username;
        this.chatMessages = chatMessages;
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Vector<ChatMessage> messages = ChatState.getChatState().getMessages();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) SimpleChat.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.messages_list, parent, false);
        }

        try {
            TextView editText_message = (TextView) row.findViewById(R.id.messagesList_editText_message);
            TextView editText_nick = (TextView) row.findViewById(R.id.messagesList_editText_nick);

            if(messages.get(position).nick.equals(username)){
                row.setBackgroundResource(R.drawable.my_bubble);
            }else{
                row.setBackgroundResource(R.drawable.bubble);
            }
            String messege= messages.get(position).message ;
            String nick= messages.get(position).nick;
            editText_message.setText(messege);
            editText_nick.setText(nick);
        } catch (Exception e) {
            Log.e(ListAdapter.class.getName(),
                    "Fallo al rellenar la lista:" + e.toString());
        }

        return (row);
    }

}


