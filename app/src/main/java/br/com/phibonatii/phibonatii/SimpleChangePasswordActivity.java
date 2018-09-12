package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SimpleChangePasswordActivity extends AppCompatActivity {

    private String token;

    public EditText fieldCurrentPassword;
    public EditText fieldNewPassword;
    public EditText fieldRepeatNewPassword;

    public String currentPassword;
    public String newPassword;
    public String repeatNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_change_password);

        token = getIntent().getStringExtra("token");
    }

    private void getFields() {
        fieldCurrentPassword = (EditText) this.findViewById(R.id.edit_currentpassword);
        fieldNewPassword = (EditText) this.findViewById(R.id.edit_newpassword);
        fieldRepeatNewPassword = (EditText) this.findViewById(R.id.edit_repeat_newpassword);

        fieldNewPassword.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldRepeatNewPassword.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void getValues() {
        getFields();
        currentPassword = fieldCurrentPassword.getText().toString();
        newPassword = fieldNewPassword.getText().toString();
        repeatNewPassword = fieldRepeatNewPassword.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        if ( !newPassword.equals(repeatNewPassword) ) {
            fieldRepeatNewPassword.setBackgroundColor(Color.parseColor("#ff0000"));
            Toast.makeText(this, "Repetição da Password NÃO foi digitada corretamente", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void changePassword(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.changePassword(token, "", "", "", "", currentPassword, newPassword, new ResponseSimpleChangePassword());
        }
    }
}

class ResponseSimpleChangePassword implements IResponseChangePassword {
    public void onPostExecute(Context context, String serverError, List<String> newPasswordErros) {
        String msgErros = "";
        SimpleChangePasswordActivity app = (SimpleChangePasswordActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            if (!newPasswordErros.isEmpty()) {
                msgErros += "New Password: ";
                for (String s : newPasswordErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldNewPassword.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (msgErros != "") {
                msgErros = "Problema de preenchimento das informações:" + "\t" + msgErros;
            }
        }

        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
        } else {
            app.finish();
        }
    }
}
