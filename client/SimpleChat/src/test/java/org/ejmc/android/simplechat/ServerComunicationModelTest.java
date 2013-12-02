package org.ejmc.android.simplechat;


import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import org.ejmc.android.simplechat.Model.ChatMessage;
import org.ejmc.android.simplechat.Model.ParseNetResultException;
import org.ejmc.android.simplechat.Model.ServerComunicationModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.HttpRequestInfo;
import org.robolectric.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 10:04
 */
@RunWith(RobolectricTestRunner.class)

public class ServerComunicationModelTest {
    private ServerComunicationModel scm;
    private String url_server;
    private String username = "test";

    @Before
    public void setUp() throws Exception {
        url_server = "http://172.16.100.188:8080";
        scm = Mockito.spy(new ServerComunicationModel(url_server,username));
    }

    @Test
    public void recoveryServerMessage() throws IOException, ParseNetResultException {
        String return_messages = "{\n" +
                "    \"messages\": [ {\"nick\":\"user1\", \"message\":\"hi there\"},\n" +
                "              {\"nick\":\"user2\", \"message\":\"hola\"} ],\n" +
                "    \"last_seq\": 6\n" +
                "}";
        Robolectric.addPendingHttpResponse(200, "OK");
        Mockito.when(scm.GET()).thenReturn(return_messages);
        Vector<ChatMessage> chatMessages = scm.getLastMessages();
        Vector<ChatMessage> expected_Chat_messages = new Vector<ChatMessage>();
        expected_Chat_messages.add(new ChatMessage("user1", "hi there"));
        expected_Chat_messages.add(new ChatMessage("user2", "hola"));
        assertArrayEquals(expected_Chat_messages.toArray(), chatMessages.toArray());
    }

    @Test
    public void httpGetWorkProperly() throws IOException{
        String return_messages = "{\n" +
                "    \"messages\": [ {\"nick\":\"user1\", \"message\":\"hi there\"},\n" +
                "              {\"nick\":\"user2\", \"message\":\"hola\"} ],\n" +
                "    \"last_seq\": 6\n" +
                "}";

        /*ProtocolVersion httpProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
        HttpResponse successResponse =
                new BasicHttpResponse(httpProtocolVersion, 200, "OK");
        try {
            successResponse.setEntity( new StringEntity(return_messages));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Robolectric.addHttpResponseRule(url_server + "chat-kata/api/chat?seq=0", successResponse);*/
        Robolectric.addPendingHttpResponse(200, return_messages);
        String messages = scm.GET();
        assertThat(messages, equalTo(return_messages));
    }

    @Test
    public void httpPostWorkProperly() throws Exception {
        String return_messages = "OK";
        Robolectric.addPendingHttpResponse(200, return_messages);
        String message_to_send = "Hola mundo!";
        String message_in_server_expected = "{\"nick\":\""+username+"\",\"message\": \""+message_to_send+"\"}";

        boolean status = scm.sendMessage(message_to_send);

        HttpRequestInfo sentHttpRequestData = Robolectric.getSentHttpRequestInfo(0);
        HttpRequest sentHttpRequest = sentHttpRequestData.getHttpRequest();
        InputStream contentStream = ((HttpPost) sentHttpRequest).getEntity().getContent();
        String message_in_server =    Strings.fromStream(contentStream);

        assertThat(message_in_server, equalTo(message_in_server_expected));

        assertThat(status, equalTo(true));
    }


}
