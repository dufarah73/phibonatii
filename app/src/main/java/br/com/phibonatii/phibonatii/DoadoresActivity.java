package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.phibonatii.phibonatii.model.Group;
import br.com.phibonatii.phibonatii.model.Phi;
import br.com.phibonatii.phibonatii.adapter.RankingAdapter;

public class DoadoresActivity extends AppCompatActivity {

    private String token;

    private List<Phi> cert;

    private int colorDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doadores);

        token = getIntent().getStringExtra("token");

        cert = new ArrayList<Phi>();
        cert.add(new Phi(Long.valueOf(14),"Bruno Ribeiro Cardoso","RG:65718655 CPF:545.299.823-35"));
        cert.add(new Phi(Long.valueOf(19),"Helvan Garcez","RG:6584115 CPF:280.154.777-86"));
        cert.add(new Phi(Long.valueOf(12),"Joao Sousa Barros Jr","RG:1877266 CPF:588.653.054-10"));
        cert.add(new Phi(Long.valueOf(16),"Julian Ferreira Martins","RG:1001234 CPF:843.373.750-31"));
        cert.add(new Phi(Long.valueOf(17),"Maria Quitéria de Jesus Medeiros","RG:8944235 CPF:738.548.483-23"));
        cert.add(new Phi(Long.valueOf(11),"Mariana Gomes Carvalho","RG:64645655 CPF:658.755.694-92"));
        cert.add(new Phi(Long.valueOf(15),"Marina Correia da Silva","RG:112398 CPF:097.529.053-31"));
        cert.add(new Phi(Long.valueOf(18),"Otávio Barbosa Azevedo","RG:65481545 CPF:102.120.412-90"));
        cert.add(new Phi(Long.valueOf(13),"Vitor Fernandes Pereira","RG:7156681 CPF:271.607.555-70"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"SEM CERTIDÃO","COM CERTIDÃO"}));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int mySpinnerPosition = position;
                if (mySpinnerPosition == 0) {
                    displayDeclarados();
                } else {
                    displayCertificados();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ListView objectList = (ListView) findViewById(R.id.listview);
        objectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int colorId = Utils.getBackgroundColor(view);
                if (colorId == Color.parseColor("#808080")) {
                    view.setBackgroundColor(colorDefault);
                } else {
                    view.setBackgroundColor(Color.parseColor("#808080"));
                    colorDefault = colorId;
                }
            }
        });
        objectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.findViewById(R.id.ranking_name);
                Intent intent = new Intent(view.getContext(), FotoActivity.class);
                intent.putExtra("doadorId", String.valueOf(id));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doadores, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doadores_menu_sair: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDeclarados() {
        WebClient webClient = new WebClient(this);
        webClient.doadores(token, false, new ResponseDoadores());
    }

    private void displayCertificados() {
        ListView objectList = (ListView) this.findViewById(R.id.listview);
        objectList.setAdapter(new RankingAdapter(this, cert));
    }
}

class ResponseDoadores implements IResponseDoadores {
    public void onPostExecute(Context context, List<Phi> doadores, String serverError) {
        DoadoresActivity app = (DoadoresActivity) context;
        ListView objectList = (ListView) app.findViewById(R.id.listview);

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            objectList.setAdapter(new RankingAdapter(app, doadores));
        }
    }
}
