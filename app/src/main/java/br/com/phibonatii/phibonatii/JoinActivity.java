package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class JoinActivity extends AppCompatActivity {

    public EditText fieldNickname;
    public EditText fieldFullName;
    public EditText fieldDateBorn;
    public EditText fieldPassAsking;
    public EditText fieldPassAnswer;
    public EditText fieldPassword;
    public EditText fieldRepeatPassword;

    public String nickName;
    public String fullName;
    public String dateBorn;
    public String passAsking;
    public String passAnswer;
    public String password;
    public String repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    private void getFields() {
        fieldNickname = (EditText) this.findViewById(R.id.edit_nickname);
        fieldFullName = (EditText) this.findViewById(R.id.edit_fullname);
        fieldDateBorn = (EditText) this.findViewById(R.id.edit_dateborn);
        fieldPassAsking = (EditText) this.findViewById(R.id.edit_passasking);
        fieldPassAnswer = (EditText) this.findViewById(R.id.edit_passanswer);
        fieldPassword = (EditText) this.findViewById(R.id.edit_password);
        fieldRepeatPassword = (EditText) this.findViewById(R.id.edit_repeat_password);

        fieldNickname.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldFullName.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldDateBorn.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldPassAsking.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldPassAnswer.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldPassword.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldRepeatPassword.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void getValues() {
        getFields();
        nickName = fieldNickname.getText().toString();
        fullName = fieldFullName.getText().toString();
        dateBorn = fieldDateBorn.getText().toString();
        passAsking = fieldPassAsking.getText().toString();
        passAnswer = fieldPassAnswer.getText().toString();
        password = fieldPassword.getText().toString();
        repeatPassword = fieldRepeatPassword.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        if ( !password.equals(repeatPassword) ) {
            fieldRepeatPassword.setBackgroundColor(Color.parseColor("#ff0000"));
            Toast.makeText(this, "Repetição da Password NÃO foi digitada corretamente", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    public void join(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.join(nickName, fullName, dateBorn, passAsking, passAnswer, password, new ResponseJoin());
        }
    }
}

class ResponseJoin implements IResponseJoin {
    public void onPostExecute(Context context, String serverError, List<String> nicknameErros, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros, List<String> passwordErros) {
        String msgErros = "";
        JoinActivity app = (JoinActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            if (!nicknameErros.isEmpty()) {
                msgErros += "Nickname: ";
                for (String s : nicknameErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldNickname.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!fullNameErros.isEmpty()) {
                msgErros += "Full Name: ";
                for (String s : fullNameErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldFullName.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!dateBornErros.isEmpty()) {
                msgErros += "Date Born: ";
                for (String s : dateBornErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldDateBorn.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!passAskingErros.isEmpty()) {
                msgErros += "Pass Asking: ";
                for (String s : passAskingErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldPassAsking.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!passAnswerErros.isEmpty()) {
                msgErros += "Pass Answer: ";
                for (String s : passAnswerErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldPassAnswer.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!passwordErros.isEmpty()) {
                msgErros += "Password: ";
                for (String s : passwordErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldPassword.setBackgroundColor(Color.parseColor("#ff0000"));
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
