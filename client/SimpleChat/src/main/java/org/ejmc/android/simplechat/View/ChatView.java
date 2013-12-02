package org.ejmc.android.simplechat.View;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import org.ejmc.android.simplechat.Model.ChatMessage;
import org.ejmc.android.simplechat.Model.ChatState;
import org.ejmc.android.simplechat.Presenter.ChatPresenter;
import org.ejmc.android.simplechat.R;

import java.util.Vector;

/**
 * Chat activity.
 *
 * @author startic
 */
public class ChatView extends Activity implements IChatView {
    private ChatState chatState;
    protected ChatPresenter chatPresenter = null;
    private ListAdapter listAdapter;
    private String username;
    private Handler internalHandler;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        configureListeners();
        configureChatState();
        configureUsernameFromIntent();
        configureListview();
        configurePresenter();
        configureMessagesHandler();
        startReadingMessageProcess();
    }

    private void configureChatState(){
        chatState = ChatState.getChatState();
    }

    private void configurePresenter() {
        if (chatPresenter == null) chatPresenter = new ChatPresenter(username);
        chatPresenter.setView(this);
    }

    private void configureUsernameFromIntent() {
        Bundle extras = getIntent().getExtras();
        String usernameConfigured = "testUser";
        if (extras != null) usernameConfigured = extras.get("Username").toString();
        TextView usernameText = (TextView) findViewById(R.id.chat_textView_username);
        usernameText.setText(usernameConfigured);
        username = usernameConfigured;
    }

    private void configureListview(){
        Vector<ChatMessage> chatMessages = chatState.getMessages();
        listAdapter  = new ListAdapter(username, chatMessages);
        listView = (ListView) findViewById(R.id.chat_listView_messages);
        listView.setAdapter(listAdapter);
    }

    private void configureMessagesHandler(){
       internalHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                listAdapter.notifyDataSetChanged();
            }
        };
    }

    private void startReadingMessageProcess() {
        chatPresenter.startReadingMessageProcess();
    }

    private void configureListeners() {
        Button button_Login = (Button) findViewById(R.id.chat_button_send);

        button_Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                sendMessage();
            }
        });

        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);

        message.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendMessage() {
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        if (message.getText().toString().length() > 0) {
            setFormVisibility(false);
            chatPresenter.sendMessage(message.getText().toString());
        }
    }

    private void setFormVisibility(boolean visibility) {
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        Button button_Send = (Button) findViewById(R.id.chat_button_send);
        ProgressBar progressBar_Send = (ProgressBar) findViewById(R.id.chat_progressBar_send);
        if (!visibility) {
            progressBar_Send.setVisibility(ProgressBar.VISIBLE);
        } else {
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
        listView.setSelection(listAdapter.getCount() - 1);
    }

    @Override
    public void messageSendedError() {
        setFormVisibility(true);
        EditText message = (EditText) this.findViewById(R.id.chat_editText_message);
        showError(getResources().getString(R.string.error_sending_message).toString());
        message.requestFocus();
    }

    @Override
    public void updateMessages() {
        Message msg =  internalHandler.obtainMessage();
        internalHandler.sendMessage(msg);
    }

    @Override
    public void showError(String errorMessage) {
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

<<<<<<< HEAD
    public Vector<Message> getMessage() {
        return messages;
    }

    public void setMessage(Vector<Message> messages) {
        this.messages = messages;
    }


    class ListaAdapter extends BaseAdapter {

        private Vector<Message> messages;


        public ListaAdapter(Vector<Message> messages) {
            this.messages = messages;

        }


        @Override
        public int getCount() {
            return messages.size();
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void addMessages(Vector<Message> messages) {
            for (Message message : messages) {
                if (this.messages.size() > 0) {
                    Message lastMessage = this.messages.lastElement();
                    if (message.nick.equals(lastMessage.nick)) lastMessage.message += "\n\n" + message.message;
                    else this.messages.add(message);
                } else {
                    this.messages.add(message);
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
                Log.e(ListaAdapter.class.getName(),
                        "Fallo al rellenar la lista:" + e.toString());
            }

            return (row);
        }

    }


=======
    @Override
    public void navigate(Class<?> destination) {}
    @Override
    public void navigate(Class<?> destination, boolean logoutFlag) {}
    @Override
    public void navigate(Class<?> destination, Bundle extras) {}
>>>>>>> MessageRefactor
}

