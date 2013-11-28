package org.ejmc.android.simplechat.View;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 25/11/13
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public interface ILoginView extends  IView{

    public void errorLoginPassword();


    void navigateToChatWhitUsername(String username);
}
