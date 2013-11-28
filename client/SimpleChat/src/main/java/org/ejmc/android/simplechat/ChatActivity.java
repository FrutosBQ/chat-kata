package org.ejmc.android.simplechat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Vector;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity implements IChatView {

    private  Vector<Message> messages;
    protected ChatPresenter chatPresenter = null;
    private ListaAdapter listaAdapter;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);


        if (chatPresenter==null) chatPresenter = new ChatPresenter();
        chatPresenter.setView(this);
        chatPresenter.startReadingMessageProcess();

        configureListeners();


        messages = new Vector<Message>();
        messages.add(new Message("pepe","hola"));
        listaAdapter = new ListaAdapter(messages);
        ListView lv = (ListView)findViewById(R.id.chat_listView_messages);
        lv.setAdapter(listaAdapter);





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
        Button button_Send = (Button) findViewById(R.id.chat_button_send);
        ProgressBar progressBar_Send = (ProgressBar) findViewById(R.id.chat_progressBar_send);
        progressBar_Send.setVisibility(1);
        button_Send.setEnabled(false);
        message.setFocusable(false);

        chatPresenter.sendMessage(message.getText().toString());
    }

    @Override
    public void messageSendedOK() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void messageSendedError() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void newMessages(Vector<Message> messages) {
        if(messages.size()>0){
            Toast toast = Toast.makeText(this, messages.get(0).message, Toast.LENGTH_SHORT);
            toast.show();
        }
        listaAdapter.addMessages(messages);
        listaAdapter.notifyDataSetChanged();

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
        //To change body of implemented methods use File | Settings | File Templates.
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
            this.messages.addAll(messages);
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
