package org.ejmc.android.simplechat.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.ejmc.android.simplechat.Presenter.ILoginPresenter;
import org.ejmc.android.simplechat.Presenter.LoginPresenter;
import org.ejmc.android.simplechat.R;
import org.ejmc.android.simplechat.SimpleChat;

/**
 * Main activity.
 * <p/>
 * Shows login config.
 */
public class LoginView extends Activity implements ILoginView {

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button_Login = (Button) findViewById(R.id.login_Button_login);

        button_Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                doLogin();
            }
        });
        loginPresenter = new LoginPresenter();
        loginPresenter.setView(this);
        loadForm();
    }

    private void doLogin() {
        //To change body of created methods use File | Settings | File Templates.
        EditText username = (EditText) this.findViewById(R.id.login_EditText_username);
        EditText password = (EditText) this.findViewById(R.id.login_EditText_password);
        loginPresenter.doLogin(username.getText().toString(), password.getText().toString());

    }

    public void setPresenter(ILoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    //IView Methods
    public void navigate(Class<?> destination) {
        Intent navigation = new Intent(this, destination);
        this.startActivity(navigation);
    }

    public void showError(String errorMessage) {
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void navigate(Class<?> destination, Bundle extras) {
        Intent navigation = new Intent(this, destination);
        navigation.putExtras(extras);
        this.startActivity(navigation);
    }

    public void navigate(Class<?> destination, boolean logoutFlag) {
        Intent intent = new Intent(this, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void errorLoginPassword() {
        showError(getString(R.string.error_login));
    }

    @Override
    public void navigateToChatWhitUsername(String username) {
        //To change body of implemented methods use File | Settings | File Templates.

        saveForm();

        Bundle bundle = new Bundle();
        bundle.putString("Username", username);
        navigate(ChatView.class, bundle);

    }

    private void saveForm() {
        String lastUsernameKey = "org.ejmc.android.simplechat.last_username";
        String lastPasswordKey = "org.ejmc.android.simplechat.last_password";

        EditText editText_password = (EditText) this.findViewById(R.id.login_EditText_password);
        EditText editText_username = (EditText) this.findViewById(R.id.login_EditText_username);

        String password = editText_password.getText().toString();
        String username = editText_username.getText().toString();

        Context context = SimpleChat.getAppContext();
        SharedPreferences preferencias= context.getSharedPreferences("org.ejmc.android.simplechat",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(lastUsernameKey, username);
        editor.putString(lastPasswordKey, password);

        editor.commit();
    }


    private void loadForm() {
        String lastUsernameKey = "org.ejmc.android.simplechat.last_username";
        String lastPasswordKey = "org.ejmc.android.simplechat.last_password";

        EditText editText_password = (EditText) this.findViewById(R.id.login_EditText_password);
        EditText editText_username = (EditText) this.findViewById(R.id.login_EditText_username);

        String password = editText_password.getText().toString();
        String username = editText_username.getText().toString();

        Context context = SimpleChat.getAppContext();
        SharedPreferences preferencias= context.getSharedPreferences("org.ejmc.android.simplechat",context.MODE_PRIVATE);

        editText_password.setText(preferencias.getString(lastPasswordKey, ""));
        editText_username.setText(preferencias.getString(lastUsernameKey, "Username"));

    }
}

