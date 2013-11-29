package org.ejmc.android.simplechat;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.ejmc.android.simplechat.Presenter.ILoginPresenter;
import org.ejmc.android.simplechat.View.ChatView;
import org.ejmc.android.simplechat.View.LoginView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginViewTest {
    private LoginView activity;
    private Button login_Button;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(LoginView.class).create().get();
        login_Button = (Button) activity.findViewById(R.id.login_Button_login);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveProperAppName() throws Exception {
        String appName = new LoginView().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("SimpleChat"));
    }

    @Test
    public void shouldHaveUsernameEditText() throws Exception {

        //Activity activity = Robolectric.buildActivity(LoginView.class).create().get();
        activity.setContentView(R.layout.activity_login);
        EditText username = (EditText) activity.findViewById(R.id.login_EditText_username);
        assertNotNull(username);
    }

    @Test
    public void shouldHavePasswordEditText() throws Exception {
        activity.setContentView(R.layout.activity_login);
        EditText password = (EditText) activity.findViewById(R.id.login_EditText_password);
        assertNotNull(password);
    }

    @Test
    public void shouldHaveLoginButton() throws Exception {
        //      activity.setContentView(R.layout.activity_login);
        assertNotNull(login_Button);
    }


    @Test
    public void clickingButtonLoginDoLogin() throws Exception {
        ILoginPresenter loginPresenterMock = mock(ILoginPresenter.class);
        activity.setPresenter(loginPresenterMock);
        EditText username = (EditText) activity.findViewById(R.id.login_EditText_username);
        EditText password = (EditText) activity.findViewById(R.id.login_EditText_password);
        username.setText("Username");
        password.setText("Password");
        login_Button.performClick();
        verify(loginPresenterMock).doLogin("Username", "Password");
    }

    @Test
    public void NavigateToChatActivity() throws Exception {

       /*activity.navigate(ChatView.class);
        Intent navigation = new Intent(activity, ChatView.class);
          assertThat(activity, new StartedMatcher(navigation));
        */

        assertEquals(activity.getClass(), LoginView.class);
        activity.navigate(ChatView.class);
      //  assertThat(activity, new StartedMatcher(ChatView.class));
    }

    @Test
    public void NavigateToChatActivityWhitBundle() throws Exception {
        Intent navigation = new Intent(activity, ChatView.class);
        navigation.putExtra("Key_username", "Value_username");
        Bundle bundle = navigation.getExtras();

        activity.navigate(ChatView.class, bundle);
      //  assertThat(activity, new StartedMatcher(navigation));

    }

    @Test
    public void ShowError() throws Exception {
        //Helper.showBadServerNotification(activity);
        activity.showError(activity.getResources().getString(R.string.error_login).toString());
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(activity.getResources().getString(R.string.error_login).toString()));
    }

    @Test
    public void ShowErrorLogin() throws Exception {
        //Helper.showBadServerNotification(activity);
        activity.errorLoginPassword();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(activity.getResources().getString(R.string.error_login).toString()));
    }


    @Test
    public void testNavigateToChatWithUsername() throws Exception {
        //Helper.showBadServerNotification(activity);
        Intent navigation = new Intent(activity, ChatView.class);
        navigation.putExtra("Username", "Value_username");
        Bundle bundle = navigation.getExtras();

        activity.navigateToChatWhitUsername("Value_username");
        //assertThat(activity, new StartedMatcher(navigation));
    }



}