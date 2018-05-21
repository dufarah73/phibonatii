package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewGroupActivity extends AppCompatActivity {

    public EditText fieldShortName;
    public EditText fieldLongName;

    public String shortName;
    public String longName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
    }
    private void getFields() {
        fieldShortName = (EditText) this.findViewById(R.id.newgr_edit_shortname);
        fieldLongName = (EditText) this.findViewById(R.id.newgr_edit_longname);

        fieldShortName.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldLongName.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void getValues() {
        getFields();
        shortName = fieldShortName.getText().toString();
        longName = fieldLongName.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void newGroup(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.newGroup(shortName, longName, new ResponseNewGroup());
        }
    }
}

class ResponseNewGroup implements IResponseNewGroup {
    public void onPostExecute(Context context, int groupId, String groupShortName, String serverError, List<String> shortNameErros, List<String> longNameErros) {
        String msgErros = "";
        NewGroupActivity app = (NewGroupActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            if (!shortNameErros.isEmpty()) {
                msgErros += "Nickname: ";
                for (String s : shortNameErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldShortName.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!longNameErros.isEmpty()) {
                msgErros += "Full Name: ";
                for (String s : longNameErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldLongName.setBackgroundColor(Color.parseColor("#ff0000"));
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
