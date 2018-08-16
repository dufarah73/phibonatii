package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity {

    private String token;

    public EditText fieldNickname;
    public EditText fieldFullName;
    public EditText fieldDateBorn;
    public EditText fieldPassAnswer;
    public EditText fieldNewPassword;

    public String nickName;
    public String fullName;
    public String dateBorn;
    public String passAnswer;
    public String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        token = getIntent().getStringExtra("token");
    }

    private void getPartialFields() {
        fieldNickname = (EditText) this.findViewById(R.id.edit_nickname);
        fieldFullName = (EditText) this.findViewById(R.id.edit_fullname);
        fieldDateBorn = (EditText) this.findViewById(R.id.edit_dateborn);
    }
    private void getPartialValues() {
        getPartialFields();
        nickName = fieldNickname.getText().toString();
        fullName = fieldFullName.getText().toString();
        dateBorn = fieldDateBorn.getText().toString();
    }
    private boolean toValidatePartial() {
        getPartialValues();
        return true;
    }

    private void getFields() {
        getPartialFields();
        fieldPassAnswer = (EditText) this.findViewById(R.id.edit_passanswer);
        fieldNewPassword = (EditText) this.findViewById(R.id.edit_newpassword);

        fieldNewPassword.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void getValues() {
        getFields();
        getPartialValues();
        passAnswer = fieldPassAnswer.getText().toString();
        newPassword = fieldNewPassword.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }

    public void changePassword(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.changePassword(token, nickName, fullName, dateBorn, passAnswer, newPassword, new ResponseChangePassword());
        }
    }

    public void retrievePassAsking(View view) {
        if (toValidatePartial()) {
            WebClient webClient = new WebClient(this);
            webClient.retrivePassAsking(token, nickName, fullName, dateBorn, new ResponseRetrievePassAsking());
        }
    }
}

class ResponseChangePassword implements IResponseChangePassword {
    public void onPostExecute(Context context, String serverError, List<String> newPasswordErros) {
        String msgErros = "";
        ChangePasswordActivity app = (ChangePasswordActivity) context;

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

class ResponseRetrievePassAsking implements IResponseRetrievePassAsking {
    public void onPostExecute(Context context, String passAsking, String serverError) {
        String msgErros = "";
        ChangePasswordActivity app = (ChangePasswordActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        }

        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
        } else {
            TextInputLayout fieldPassAnswer = (TextInputLayout) app.findViewById(R.id.textinputlayout_passanswer);
            fieldPassAnswer.setHint(passAsking);
        }
    }
}
