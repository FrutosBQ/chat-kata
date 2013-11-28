package org.ejmc.android.simplechat.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.SimpleChat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 10:04
 */
public class ServerComunicationModel {
    private final String url_server;
    private final String username;
    public int last_seq=0;

    public ServerComunicationModel(String url_server, String username) {
        this.url_server = url_server;
        this.username   = username;
        RecoverLastSeq();
    }

    private void RecoverLastSeq(){
        String lastSeqKey = "org.ejmc.android.simplechat.last_seq";
        Context context = SimpleChat.getAppContext();
        SharedPreferences preferences = context.getSharedPreferences("org.ejmc.android.simplechat",context.MODE_PRIVATE);
        last_seq = preferences.getInt(lastSeqKey, 0);
    }

    private void StoreLastSeq(){
        String lastSeqKey = "org.ejmc.android.simplechat.last_seq";
        Context context = SimpleChat.getAppContext();
        SharedPreferences preferencias= context.getSharedPreferences("org.ejmc.android.simplechat",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt(lastSeqKey, last_seq);
        editor.commit();
    }

    public String GET() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet del =new HttpGet(url_server+"/chat-kata/api/chat?next_seq="+last_seq);

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
            StoreLastSeq();
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

    public boolean sendMessage(String message_text) throws Exception {
        String json = generateJSON(message_text);
        return POST(json);
    }

    public boolean POST(String message_to_send) {
        HttpClient hc = new DefaultHttpClient();
        HttpPost p = new HttpPost(url_server+"/chat-kata/api/chat");
        boolean result=false;
        try {
            p.setEntity(new StringEntity(message_to_send, "UTF8"));
            p.setHeader("Content-type", "application/json");
            HttpResponse resp = hc.execute(p);
            if (resp != null) {
                if (resp.getStatusLine().getStatusCode() == 200)
                    result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;

    }


    private String generateJSON(String message_to_send) {
        String JSon = "{\"nick\":\"" +
                 username +
                "\",\"message\": \"" +
                message_to_send
                + "\"}";
        return JSon;
    }
}
