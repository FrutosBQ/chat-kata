package org.ejmc.android.simplechat;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.matchers.StartedMatcher;
import com.xtremelabs.robolectric.shadows.ShadowToast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    private LoginActivity activity;
    private Button login_Button;

    @Before
    public void setUp() throws Exception {
        activity = new LoginActivity();
        activity.onCreate(null);
        login_Button = (Button) activity.findViewById(R.id.login_Button_login);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveProperAppName() throws Exception {
        String appName = new LoginActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("SimpleChat"));
    }

    @Test
    public void shouldHaveUsernameEditText() throws Exception {

        //Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
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

       /*activity.navigate(ChatActivity.class);
        Intent navigation = new Intent(activity, ChatActivity.class);
          assertThat(activity, new StartedMatcher(navigation));
        */

        assertEquals(activity.getClass(), LoginActivity.class);
        activity.navigate(ChatActivity.class);
        assertThat(activity, new StartedMatcher(ChatActivity.class));
    }

    @Test
    public void NavigateToChatActivityWhitBundle() throws Exception {
        Intent navigation = new Intent(activity, ChatActivity.class);
        navigation.putExtra("Key_username", "Value_username");
        Bundle bundle = navigation.getExtras();

        activity.navigate(ChatActivity.class, bundle);
        assertThat(activity, new StartedMatcher(navigation));

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
        Intent navigation = new Intent(activity, ChatActivity.class);
        navigation.putExtra("Username", "Value_username");
        Bundle bundle = navigation.getExtras();

        activity.navigateToChatWhitUsername("Value_username");
        assertThat(activity, new StartedMatcher(navigation));
    }



}