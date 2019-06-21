package br.com.phibonatii.phibonatii;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DoadorActivity extends AppCompatActivity {

    public static final int PARENTE_REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int CAMERA_PERMISSION_CODE = 202;

    public EditText fieldNome;
    public EditText fieldRG;
    public EditText fieldCPF;
    public EditText fieldNascimento;
    public EditText fieldNaturalidade;

    public String nome;
    public String rg;
    public String cpf;
    public String nascimento;
    public String naturalidade;

    public Boolean gerarpdf;
    public Boolean fotografar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doador);

        getFields();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doador, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doador_menu_login: {
                login();
                break;
            }
            case R.id.doador_menu_sair: {
                limpar();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PARENTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String qtdParentes = data.getStringExtra("qtdParentes");
                if (Integer.valueOf(qtdParentes) < 1) {
                    Toast.makeText(this, "Indique algum parente (pelo menos 1) antes de gerar declaração", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Gere, imprima, assine, e fotografe formulário de declaração", Toast.LENGTH_LONG).show();
                }
                fieldNome.requestFocus();
            }
        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Foto enviada", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void limpar() {
        getFields();

        fieldNome.setText("");
        fieldRG.setText("");
        fieldCPF.setText("");
        fieldNascimento.setText("");
        fieldNaturalidade.setText("");
        fieldNome.requestFocus();
    }

    private void getFields() {
        fieldNome = (EditText) this.findViewById(R.id.edit_nome);
        fieldRG = (EditText) this.findViewById(R.id.edit_rg);
        fieldCPF = (EditText) this.findViewById(R.id.edit_cpf);
        fieldNascimento = (EditText) this.findViewById(R.id.edit_nascimento);
        fieldNaturalidade = (EditText) this.findViewById(R.id.edit_naturalidade);

        Utils.cleanErrorOnField(fieldNome);
        Utils.cleanErrorOnField(fieldRG);
        Utils.cleanErrorOnField(fieldCPF);
        Utils.cleanErrorOnField(fieldNascimento);
        Utils.cleanErrorOnField(fieldNaturalidade);
    }
    private void getValues() {
        getFields();

        nome = fieldNome.getText().toString();
        rg = fieldRG.getText().toString();
        cpf = fieldCPF.getText().toString();
        nascimento = fieldNascimento.getText().toString();
        naturalidade = fieldNaturalidade.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void cadastrado(boolean cadastrado, boolean foto) {
        gerarpdf = cadastrado;
        fotografar = foto;
        if (toValidate()) {
            Button button1 = (Button) this.findViewById(R.id.button_doador);
            button1.setEnabled(false);
            Button button2 = (Button) this.findViewById(R.id.button_pdf);
            button2.setEnabled(false);
            Button button3 = (Button) this.findViewById(R.id.button_foto);
            button3.setEnabled(false);
            WebClient webClient = new WebClient(this);
            webClient.doador(cadastrado, nome, rg, cpf, nascimento, naturalidade, new ResponseDoador());
        }
    }
    public void doador(View view) {
        cadastrado(false, false);
    }
    public void pdf(View view) {
        cadastrado(true, false);
    }
    public void foto(View view) {
        cadastrado(true, true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "CAMERA - permissão negada", Toast.LENGTH_LONG).show();
            }
        }
    }
}

class ResponseDoador implements IResponseDoador {
    public void onPostExecute(Context context, String doadorId, String qtdParentes, String serverError, List<String> nomeErros, List<String> rgErros, List<String> cpfErros, List<String> nascimentoErros, List<String> naturalidadeErros) {
        String msgErros = "";
        DoadorActivity app = (DoadorActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            msgErros += Utils.putErrorOnField(nomeErros, app.fieldNome, app.getResources().getString(R.string.hint_nome_doador));
            msgErros += Utils.putErrorOnField(rgErros, app.fieldRG, app.getResources().getString(R.string.hint_rg_doador));
            msgErros += Utils.putErrorOnField(cpfErros, app.fieldCPF, app.getResources().getString(R.string.hint_cpf_doador));
            msgErros += Utils.putErrorOnField(nascimentoErros, app.fieldNascimento, app.getResources().getString(R.string.hint_nascimento_doador));
            msgErros += Utils.putErrorOnField(naturalidadeErros, app.fieldNaturalidade, app.getResources().getString(R.string.hint_naturalidade_doador));
            if (msgErros != "") {
                msgErros = "Problema de preenchimento das informações:" + "\t" + msgErros;
            }
        }

        Button button1 = (Button) app.findViewById(R.id.button_doador);
        button1.setEnabled(true);
        Button button2 = (Button) app.findViewById(R.id.button_pdf);
        button2.setEnabled(true);
        Button button3 = (Button) app.findViewById(R.id.button_foto);
        button3.setEnabled(true);
        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
        } else {
            if (app.fotografar) {
                if (Integer.valueOf(qtdParentes) < 1) {
                    Toast.makeText(context, "Primeiro imprima e assine o formulário de declaração", Toast.LENGTH_LONG).show();
                } else {
                    if (ActivityCompat.checkSelfPermission(app, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(app, new String[]{Manifest.permission.CAMERA}, app.CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        app.startActivityForResult(cameraIntent, app.CAMERA_REQUEST_CODE);
                    }
                }
            } else
            if (app.gerarpdf) {
                if (Integer.valueOf(qtdParentes) < 1) {
                    Toast.makeText(context, "Primeiro cadastre pelo menos 1 parente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Gerar PDF", Toast.LENGTH_LONG).show();
                }
            } else {
                Intent intent = new Intent(app, ParenteActivity.class);
                intent.putExtra("doadorId", doadorId);
                intent.putExtra("doadorNome", app.nome);
                intent.putExtra("qtdParentes", qtdParentes);
                app.startActivityForResult(intent, app.PARENTE_REQUEST_CODE);
            }
        }
    }
}
