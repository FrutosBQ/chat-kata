package org.ejmc.android.simplechat;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 25/11/13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {
    private LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        loginPresenter = new LoginPresenter();


    }

    @Test
    public void testDoLogin() throws Exception {
        ILoginView loginViewMock = mock(ILoginView.class);
        loginPresenter.setView(loginViewMock);
        loginPresenter.doLogin("username", "password");
        verify(loginViewMock).navigateToChatWhitUsername("username");
    }

    @Test
    public void testDoLoginUsernameEmpty() throws Exception {
        ILoginView loginViewMock = mock(ILoginView.class);
        loginPresenter.setView(loginViewMock);
        loginPresenter.doLogin("", "password");
        verify(loginViewMock).errorLoginPassword();
    }
    @Test
    public void testDoLoginPasswordEmpty() throws Exception {
        ILoginView loginViewMock = mock(ILoginView.class);
        loginPresenter.setView(loginViewMock);
        loginPresenter.doLogin("Username", "");
        verify(loginViewMock).errorLoginPassword();
    }
}
