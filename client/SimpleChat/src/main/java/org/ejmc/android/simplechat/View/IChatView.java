package org.ejmc.android.simplechat.View;

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

    void updateMessages();
}
