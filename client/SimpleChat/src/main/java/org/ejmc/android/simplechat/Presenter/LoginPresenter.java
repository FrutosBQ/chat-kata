package org.ejmc.android.simplechat.Presenter;

import org.ejmc.android.simplechat.View.ILoginView;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 25/11/13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;

    public void setView(ILoginView loginView) {
        this.view = loginView;
    }

    @Override
    public void doLogin(String username, String password) {
        try {
            testNotEmptyString(username);
            testNotEmptyString(password);
            view.navigateToChatWhitUsername(username);
        } catch (Exception e) {
            view.errorLoginPassword();
        }
    }

    private void testNotEmptyString(String username) throws Exception {
        if (username.length() < 1) throw new Exception();
    }


}
