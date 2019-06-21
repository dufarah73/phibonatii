package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.phibonatii.phibonatii.model.Group;

public class ParenteActivity extends AppCompatActivity {

    public EditText fieldNome;
    public EditText fieldParentesco;
    public EditText fieldEndereco;
    public EditText fieldContato;
    public TextView textParenteSq;

    public String nome;
    public String parentesco;
    public String endereco;
    public String contato;

    public String doadorId;
    public String doadorNome;
    public String qtdParentes;
    public Boolean ultimoParente;
    public Integer parenteSq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parente);

        getFields();

        doadorId = getIntent().getStringExtra("doadorId");
        doadorNome = getIntent().getStringExtra("doadorNome");
        qtdParentes = getIntent().getStringExtra("qtdParentes");

        parenteSq = Integer.valueOf(qtdParentes)+1;
        textParenteSq = (TextView) this.findViewById(R.id.text_sequencia);
        textParenteSq.setText("Parente #"+String.valueOf(parenteSq)+" de "+doadorNome);
    }

    private void getFields() {
        fieldNome = (EditText) this.findViewById(R.id.edit_nome);
        fieldParentesco = (EditText) this.findViewById(R.id.edit_parentesco);
        fieldEndereco = (EditText) this.findViewById(R.id.edit_endereco);
        fieldContato = (EditText) this.findViewById(R.id.edit_contato);

        Utils.cleanErrorOnField(fieldNome);
        Utils.cleanErrorOnField(fieldParentesco);
        Utils.cleanErrorOnField(fieldEndereco);
        Utils.cleanErrorOnField(fieldContato);
    }
    private void getValues() {
        getFields();
        nome = fieldNome.getText().toString();
        parentesco = fieldParentesco.getText().toString();
        endereco = fieldEndereco.getText().toString();
        contato = fieldContato.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void qualquerParente(boolean ultimo) {
        ultimoParente = ultimo;
        if (toValidate()) {
            Button button1 = (Button) this.findViewById(R.id.button_parente);
            button1.setEnabled(false);
            Button button2 = (Button) this.findViewById(R.id.button_ultimoparente);
            button2.setEnabled(false);
            WebClient webClient = new WebClient(this);
            webClient.parente(nome, parentesco, endereco, contato, doadorId, new ResponseParente());
        }
    }
    public void parente(View view) {
        qualquerParente(false);
    }
    public void ultimoParente(View view) {
        qualquerParente(true);
    }
}

class ResponseParente implements IResponseParente {
    public void onPostExecute(Context context, String serverError, List<String> nomeErros, List<String> parentescoErros, List<String> enderecoErros, List<String> contatoErros) {
        String msgErros = "";
        ParenteActivity app = (ParenteActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            msgErros += Utils.putErrorOnField(nomeErros, app.fieldNome, app.getResources().getString(R.string.hint_nome_parente));
            msgErros += Utils.putErrorOnField(parentescoErros, app.fieldParentesco, app.getResources().getString(R.string.hint_parentesco_parente));
            msgErros += Utils.putErrorOnField(enderecoErros, app.fieldEndereco, app.getResources().getString(R.string.hint_endereco_parente));
            msgErros += Utils.putErrorOnField(contatoErros, app.fieldContato, app.getResources().getString(R.string.hint_contato_parente));
            if (msgErros != "") {
                msgErros = "Problema de preenchimento das informações:" + "\t" + msgErros;
            }
        }

        Button button1 = (Button) app.findViewById(R.id.button_parente);
        button1.setEnabled(true);
        Button button2 = (Button) app.findViewById(R.id.button_ultimoparente);
        button2.setEnabled(true);
        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
        } else {
            if (app.ultimoParente) {
                app.getIntent().putExtra("qtdParentes", String.valueOf(app.parenteSq));
                app.setResult(Activity.RESULT_OK, app.getIntent());
                app.finish();
            } else {
                Toast.makeText(context, "Parente salvo. Inicie digitação de mais outro.", Toast.LENGTH_LONG).show();

                app.fieldNome.setText("");
                app.fieldParentesco.setText("");
                app.fieldEndereco.setText("");
                app.fieldContato.setText("");
                app.fieldNome.requestFocus();

                app.parenteSq += 1;
                app.textParenteSq.setText("Parente #"+String.valueOf(app.parenteSq)+" de "+app.doadorNome);
            }
        }
    }
}
