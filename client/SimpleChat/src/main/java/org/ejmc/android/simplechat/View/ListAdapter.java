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

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) SimpleChat.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.messages_list, parent, false);
        }

        try {
            TextView name_lbl = (TextView) row.findViewById(R.id.messagesList_editText_message);
            if (chatMessages.get(position).nick.equals(username)) row.setBackgroundResource(R.drawable.my_bubble);
            String messege_html = "<b>" + chatMessages.get(position).nick + " :</b> " + chatMessages.get(position).message;
            name_lbl.setText(Html.fromHtml(messege_html));
        } catch (Exception e) {
            Log.e(ListAdapter.class.getName(),
                    "Fallo al rellenar la lista:" + e.toString());
        }

        return (row);
    }

}


