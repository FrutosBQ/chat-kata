package org.ejmc.android.simplechat.Model;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
import com.google.gson.annotations.SerializedName;

import java.util.Vector;

public class Response {
    @SerializedName("messages")
    public Vector<ChatMessage> messages;
    @SerializedName("nextSeq")
    public int nextSeq;
}