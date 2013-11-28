package org.ejmc.android.simplechat;


import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.tester.org.apache.http.FakeHttpLayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)

public class ServerComunicationModelTest {


    private ServerComunicationModel scm;
    private String url_server;

    @Before
    public void setUp() throws Exception {
        url_server = "http://172.16.100.188:8080";
        //scm = new ServerComunicationModel(url_server);
        scm = Mockito.spy(new ServerComunicationModel(url_server));
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
        Vector<Message> messages = scm.getLastMessages();
        Vector<Message> expected_messages = new Vector<Message>();
        expected_messages.add(new Message("user1", "hi there"));
        expected_messages.add(new Message("user2", "hola"));
        assertArrayEquals(expected_messages.toArray(), messages.toArray());
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
}
