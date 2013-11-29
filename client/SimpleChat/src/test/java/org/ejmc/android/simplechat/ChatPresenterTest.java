package org.ejmc.android.simplechat;

import org.ejmc.android.simplechat.Model.Message;
import org.ejmc.android.simplechat.Model.ServerComunicationModel;
import org.ejmc.android.simplechat.Presenter.ChatPresenter;
import org.ejmc.android.simplechat.View.ChatView;
import org.ejmc.android.simplechat.View.IChatView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Vector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class ChatPresenterTest {

    private ChatPresenter chatPresenter;
    private String username = "TestUser";

    @Before
    public void setUp(){
        chatPresenter = new ChatPresenter(username);
    }


    @Test
    public void shouldGetFirstsMessages() throws Exception {
        IChatView chatViewMock = mock(IChatView.class);
        ServerComunicationModel scmMock = mock(ServerComunicationModel.class);

        Vector<Message> messages = new Vector<Message>();
        messages.add(new Message("nick","text"));

        Mockito.when(scmMock.getLastMessages()).thenReturn(messages);

        chatPresenter.setView(chatViewMock);
        chatPresenter.setScm(scmMock);

        chatPresenter.startReadingMessageProcess();

        Robolectric.runUiThreadTasksIncludingDelayedTasks();

        verify(chatViewMock).newMessages(messages);
    }

    @Test
    public void shouldSendTheMessageToTheModel() throws Exception {
        String message_text = "text message";
        ServerComunicationModel scmMock = mock(ServerComunicationModel.class);
        ChatView chatViewMock = mock(ChatView.class);

        chatPresenter.setScm(scmMock);
        chatPresenter.setView(chatViewMock);
        chatPresenter.sendMessage(message_text);

        //Robolectric.runUiThreadTasksIncludingDelayedTasks();
        verify(scmMock).sendMessage(message_text);
    }

}
