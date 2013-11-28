package org.ejmc.android.simplechat.Presenter;

import org.ejmc.android.simplechat.View.ILoginView;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 25/11/13
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public interface ILoginPresenter {
    void doLogin(String username, String password);
    void setView(ILoginView loginView);
}
