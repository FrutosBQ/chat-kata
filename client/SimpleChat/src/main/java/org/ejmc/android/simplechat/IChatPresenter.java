package org.ejmc.android.simplechat;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 27/11/13
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public interface IChatPresenter {
    void setView(IChatView chatView);
    void startReadingMessageProcess();
    void sendMessage(String message);

}
