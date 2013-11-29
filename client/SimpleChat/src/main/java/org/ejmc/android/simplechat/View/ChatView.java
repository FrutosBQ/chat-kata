package org.ejmc.android.simplechat.View;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.ejmc.android.simplechat.Presenter.ChatPresenter;
import org.ejmc.android.simplechat.Model.Message;
import org.ejmc.android.simplechat.R;

import java.util.Vector;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatView extends Activity implements IChatView {

    private  Vector<Message> messages;
    protected ChatPresenter chatPresenter = null;
    private ListaAdapter listaAdapter;
    private String username;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

        configureListeners();

        messages = new Vector<Message>();
        listaAdapter = new ListaAdapter(messages);
        ListView lv = (ListView)findViewById(R.id.chat_listView_messages);
        lv.setAdapter(listaAdapter);


        Bundle extras = getIntent().getExtras();
        String usernameConfigured = "testUser";
        if (extras!=null)usernameConfigured = extras.get("Username").toString();

        TextView usernameText = (TextView)findViewById(R.id.chat_textView_username);

        usernameText.setText(usernameConfigured);
        username = usernameConfigured;

        if (chatPresenter==null) chatPresenter = new ChatPresenter(usernameConfigured);
        chatPresenter.setView(this);
        startReadingMessageProcess();
    }

    public void startReadingMessageProcess() {
        chatPresenter.startReadingMessageProcess();
    }


    private void configureListeners() {
        Button button_Login = (Button) findViewById(R.id.chat_button_send);

        button_Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        //To change body of created methods use File | Settings | File Templates.
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        if(message.getText().toString().length()>0){
            setFormVisibility(false);
            chatPresenter.sendMessage(message.getText().toString());
        }
    }

    private void setFormVisibility(boolean visibility) {
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        Button button_Send = (Button) findViewById(R.id.chat_button_send);
        ProgressBar progressBar_Send = (ProgressBar) findViewById(R.id.chat_progressBar_send);
        if(!visibility){
            progressBar_Send.setVisibility(ProgressBar.VISIBLE);
        }
        else{
            progressBar_Send.setVisibility(ProgressBar.INVISIBLE);
        }
        button_Send.setEnabled(visibility);
        message.setFocusable(visibility);
        message.setFocusableInTouchMode(visibility);
    }

    @Override
    public void messageSendedOK() {
        setFormVisibility(true);
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        message.setText("");
        message.requestFocus();
        //To change body of implemented methods use File | Settings | File Templates.
        ListView lv = (ListView)findViewById(R.id.chat_listView_messages);
        lv.setSelection(listaAdapter.getCount()-1);
    }

    @Override
    public void messageSendedError() {
        setFormVisibility(true);
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);

        showError(getResources().getString(R.string.error_sending_message).toString());
        message.requestFocus();
        //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public void newMessages(Vector<Message> messages) {
        boolean listViewInTheBottom=false;
        ListView lv = (ListView)findViewById(R.id.chat_listView_messages);
        if(lv.getLastVisiblePosition()==listaAdapter.getCount()-1)     listViewInTheBottom= true;
        listaAdapter.addMessages(messages);
        listaAdapter.notifyDataSetChanged();
        if(listViewInTheBottom && messages.size()>0) lv.setSelection(listaAdapter.getCount()-1);
    }

    @Override
    public void navigate(Class<?> destination) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void navigate(Class<?> destination, boolean logoutFlag) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void navigate(Class<?> destination, Bundle extras) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showError(String errorMessage) {
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    public Vector<Message> getMessage() {
        return messages;
    }

    public void setMessage(Vector<Message> messages) {
        this.messages=messages;
    }


    class ListaAdapter extends BaseAdapter {

        private Vector<Message> messages;


        public ListaAdapter(Vector<Message> messages){
            this.messages = messages;

        }


        @Override
        public int getCount() {
            return   messages.size();
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return  messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void addMessages(Vector<Message> messages){
            if (messages != null){
                for (Message message : messages ) {
                    if(this.messages.size()>0){
                        Message lastMessage = this.messages.lastElement();
                        if (message.nick.equals(lastMessage.nick)) lastMessage.message+= "\n\n" + message.message;
                        else this.messages.add(message);
                    }else{
                        this.messages.add(message);
                    }
                }
            }
                //this.messages.addAll(messages)
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();

                row = inflater.inflate(R.layout.messages_list, parent, false);
            }

            try {
                TextView name_lbl = (TextView) row.findViewById(R.id.messagesList_editText_message);
                name_lbl.setText(messages.get(position).nick +" : "+messages.get(position).message);


            }
            catch (Exception e) {
                Log.e(ListaAdapter.class.getName(),
                        "Fallo al rellenar la lista:" + e.toString());
            }

            return (row);
        }

    }



}

