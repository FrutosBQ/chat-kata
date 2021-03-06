package org.ejmc.android.simplechat;

import android.os.Bundle;
import android.widget.*;
import org.ejmc.android.simplechat.Model.ChatMessage;
import org.ejmc.android.simplechat.Model.ChatState;
import org.ejmc.android.simplechat.Presenter.ChatPresenter;
import org.ejmc.android.simplechat.View.ChatView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import java.util.Vector;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class ChatViewTest {
    private ChatViewMock activity;
    private ChatPresenter chatPresenterMock;


    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(ChatViewMock.class).create().get();
        chatPresenterMock = activity.getChatPresenterMock();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveUsernameTextView() throws Exception {
        TextView username = (TextView) activity.findViewById(R.id.chat_textView_username);
        assertNotNull(username);
    }

    @Test
    public void shouldHaveMessageEditText() throws Exception {

        EditText message = (EditText) activity.findViewById(R.id.chat_editText_message);
        assertNotNull(message);
    }
    @Test
    public void shouldHaveSendButton() throws Exception {

        Button send = (Button) activity.findViewById(R.id.chat_button_send);
        assertNotNull(send);
    }


    @Test
    public void sendMessagesToTheChatPresenter(){
        EditText message = (EditText) activity.findViewById(R.id.chat_editText_message);
        String message_to_send = "Test Send";
        message.setText(message_to_send);
        Button send = (Button) activity.findViewById(R.id.chat_button_send);
        send.performClick();
        ProgressBar progressBar_Send = (ProgressBar) activity.findViewById(R.id.chat_progressBar_send);

        verify(chatPresenterMock).sendMessage(message_to_send);
        assertFalse(message.isFocusableInTouchMode());
        assertFalse(send.isEnabled());
        //assertThat(progressBar_Send.getVisibility(), equalTo(1));

    }

    @Test
    public void MessageSendedError(){
        EditText message = (EditText) activity.findViewById(R.id.chat_editText_message);
        String message_to_send = "Test Send";
        message.setText(message_to_send);
        Button send = (Button) activity.findViewById(R.id.chat_button_send);
        send.performClick();
        ProgressBar progressBar_Send = (ProgressBar) activity.findViewById(R.id.chat_progressBar_send);

        activity.messageSendedError();

        assertTrue(message.isFocusableInTouchMode());
        assertTrue(send.isEnabled());
        assertThat(message_to_send, equalTo(message.getText().toString()));

        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(activity.getResources().getString(R.string.error_sending_message).toString()));

        //assertThat(progressBar_Send.getVisibility(), equalTo(0));
    }
    @Test
    public void MessageSendedOk(){
        EditText message = (EditText) activity.findViewById(R.id.chat_editText_message);
        String message_to_send = "Test Send";
        message.setText(message_to_send);
        Button send = (Button) activity.findViewById(R.id.chat_button_send);
        send.performClick();
        ProgressBar progressBar_Send = (ProgressBar) activity.findViewById(R.id.chat_progressBar_send);

        activity.messageSendedOK();

        assertTrue(message.isFocusableInTouchMode());
        assertTrue(send.isEnabled());
        assertThat("", equalTo(message.getText().toString()));


        //assertThat(progressBar_Send.getVisibility(), equalTo(0));
    }

    @Test
    public void addMessagesToTheListView(){

        Vector<ChatMessage> messages = new Vector<ChatMessage>();
        messages.add(new ChatMessage("nick1","message1"));
        messages.add(new ChatMessage("nick2","message2"));
        ChatState.getChatState().addMessages(messages);

        ((ChatView) activity).updateMessages();
        ListView messagesListView = (ListView) activity.findViewById(R.id.chat_listView_messages);

        for(int i=0;i<messages.size();i++){
             assertThat(messages.get(i),equalTo(messagesListView.getItemAtPosition(i)));
        }
    }
}


class ChatViewMock extends ChatView {
    public void onCreate(Bundle bundle){
        chatPresenter = Mockito.mock(ChatPresenter.class);
        super.onCreate(bundle);
    }
    public ChatPresenter getChatPresenterMock(){
        return  chatPresenter;
    }

}