package org.ejmc.android.simplechat.View;

import org.ejmc.android.simplechat.Model.Message;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public interface IChatView extends IView {

    void messageSendedOK();

    void messageSendedError();

    void newMessages(Vector<Message> messages);
}
