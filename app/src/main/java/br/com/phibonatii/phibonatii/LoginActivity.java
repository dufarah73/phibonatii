package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public EditText fieldNickName;
    public EditText fieldPassword;

    public String nickName;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    private void getFields() {
        fieldNickName = (EditText) this.findViewById(R.id.login_edit_nickname);
        fieldPassword = (EditText) this.findViewById(R.id.login_edit_password);
    }
    private void getValues() {
        getFields();
        nickName = fieldNickName.getText().toString();
        password = fieldPassword.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void login(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.login(nickName, password, new ResponseLogin());
        }
    }

    public void changePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
    public void join(View view) {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }
}

class ResponseLogin implements IResponseLogin {
    public void onPostExecute(Context context, String token, List<String> groups, String serverError) {
        LoginActivity app = (LoginActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(app, MainActivity.class);
            intent.putExtra("token",token);
            intent.putExtra("groups", groups.toArray(new String[groups.size()]));
            app.startActivity(intent);
        }
    }
}
