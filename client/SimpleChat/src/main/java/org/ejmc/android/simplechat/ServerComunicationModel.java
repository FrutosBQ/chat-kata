package org.ejmc.android.simplechat;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class ServerComunicationModel {

    private final String url_server;
    public int last_seq=0;
    public ServerComunicationModel(String url_server) {
        //To change body of created methods use File | Settings | File Templates.
        this.url_server = url_server;
    }

    public String GET() throws IOException {
         HttpClient httpClient = new DefaultHttpClient();

        HttpGet del =new HttpGet(url_server+"/chat-kata/api/chat?seq="+last_seq);

        del.setHeader("content-type", "application/json");
        HttpResponse resp = httpClient.execute(del);
        String respStr = EntityUtils.toString(resp.getEntity());

        return respStr;  //To change body of created methods use File | Settings | File Templates.

    }

    public Vector<Message> getLastMessages() throws IOException, ParseNetResultException {
        String json = GET();
        return parserJSON(json);
    }

    private Vector<Message> parserJSON(String json) throws ParseNetResultException {
        try{
            Gson gson = new Gson();
            Response response = gson.fromJson(json, Response.class );
            last_seq = response.nextSeq;
            return response.Messages;
        } catch (Exception e) {
            throw new ParseNetResultException();
        }
    }




    private Vector<Message> parserJSONSinGSON(String json) {
        Vector<Message> result_messages = new Vector<Message>();

        JSONObject respJSON = null;
        try {
            respJSON = new JSONObject(json);
            JSONArray messages_json = respJSON.getJSONArray("messages");
            for(int i=0; i<messages_json.length(); i++)
            {
                JSONObject obj = messages_json.getJSONObject(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return  null;
        }
        return result_messages;  //To change body of created methods use File | Settings | File Templates.
    }

    public void sendMessage(String message_text) throws Exception {

        throw new Exception("Stub!!");


    }
}
