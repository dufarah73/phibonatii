package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.phibonatii.phibonatii.model.Group;

public class ParamsActivity extends AppCompatActivity {

    private String token;

    public EditText fieldPrevisaoChuva;
    public EditText fieldAlturaMare;
    public EditText fieldNivelRio;
    public EditText fieldIndicePluviometrico;

    public String previsaoChuva;
    public String alturaMare;
    public String nivelRio;
    public String indicePluviometrico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);

        token = getIntent().getStringExtra("token");
    }

    private void getFields() {
        fieldPrevisaoChuva = (EditText) this.findViewById(R.id.edit_previsaochuva);
        fieldAlturaMare = (EditText) this.findViewById(R.id.edit_alturamare);
        fieldNivelRio = (EditText) this.findViewById(R.id.edit_nivelrio);
        fieldIndicePluviometrico = (EditText) this.findViewById(R.id.edit_indicepluviometrico);

        fieldPrevisaoChuva.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldAlturaMare.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldNivelRio.setBackgroundColor(Color.parseColor("#ffffff"));
        fieldIndicePluviometrico.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    private void getValues() {
        getFields();
        previsaoChuva = fieldPrevisaoChuva.getText().toString();
        alturaMare = fieldAlturaMare.getText().toString();
        nivelRio = fieldNivelRio.getText().toString();
        indicePluviometrico = fieldIndicePluviometrico.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void params(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.params(token, previsaoChuva, alturaMare, nivelRio, indicePluviometrico, new ResponseParams());
        }
    }
}

class ResponseParams implements IResponseParams {
    public void onPostExecute(Context context, List<Group> groups, String serverError, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros) {
        String msgErros = "";
        ParamsActivity app = (ParamsActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            if (!fullNameErros.isEmpty()) {
                msgErros += "Previsão de Chuva: ";
                for (String s : fullNameErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldPrevisaoChuva.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!dateBornErros.isEmpty()) {
                msgErros += "Altura da Maré: ";
                for (String s : dateBornErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldAlturaMare.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!passAskingErros.isEmpty()) {
                msgErros += "Nível do Rio: ";
                for (String s : passAskingErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldNivelRio.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (!passAnswerErros.isEmpty()) {
                msgErros += "Índice Pluviométrico: ";
                for (String s : passAnswerErros) {
                    msgErros = msgErros + s + "\t";
                }
                app.fieldIndicePluviometrico.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if (msgErros != "") {
                msgErros = "Problema de preenchimento das informações:" + "\t" + msgErros;
            }
        }

        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
        } else {
            app.getIntent().putExtra("groups",(ArrayList<Group>) groups);
            app.setResult(Activity.RESULT_OK, app.getIntent());
            app.finish();
        }
    }
}
