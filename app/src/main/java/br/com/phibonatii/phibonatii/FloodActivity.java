package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.phibonatii.phibonatii.model.Group;

public class FloodActivity extends AppCompatActivity {

    private String token;

    private TextView text1, text2, text3, text4;

    private TextView textStatus;
    private ImageView imageStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood);

        token = getIntent().getStringExtra("token");

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        text1 = (TextView) this.findViewById(R.id.text1);
        text2 = (TextView) this.findViewById(R.id.text2);
        text3 = (TextView) this.findViewById(R.id.text3);
        text4 = (TextView) this.findViewById(R.id.text4);

        setTextParams((List<Group>) getIntent().getSerializableExtra("groups"));

        textStatus = (TextView) this.findViewById(R.id.textView);
        imageStatus = (ImageView) this.findViewById(R.id.imageView);

        textStatus.setText("Normal");
        imageStatus.setImageResource(R.drawable.ic_action_normal);
    }

    public void alterar(View view) {
        if (textStatus.getText().toString() == "Normal") {
            textStatus.setText("Atenção");
            imageStatus.setImageResource(R.drawable.ic_action_atencao);
        } else {
            if (textStatus.getText().toString() == "Atenção") {
                textStatus.setText("Emergência");
                imageStatus.setImageResource(R.drawable.ic_action_emergencia);
            } else {
                textStatus.setText("Normal");
                imageStatus.setImageResource(R.drawable.ic_action_normal);
            }
        }

    }

    public void changeCity() {
        Intent intent = new Intent(this, MapaActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void changePassword() {
        Intent intent = new Intent(this, SimpleChangePasswordActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void params() {
        Intent intent = new Intent(this, ParamsActivity.class);
        intent.putExtra("token", token);
        startActivityForResult(intent, 1 /*Params*/);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            setTextParams((List<Group>) data.getSerializableExtra("groups"));
        }
    }

    protected void setTextParams(List<Group> groups) {
        for (int i = 0 ; i < groups.size() ; i++) {
            switch (groups.get(i).getId().intValue()) {
                case 1: {
                    text1.setText(groups.get(i).getLongName()+": "+groups.get(i).getShortName()+" %");
                    break;
                }
                case 2: {
                    text2.setText(groups.get(i).getLongName()+": "+groups.get(i).getShortName()+" m");
                    break;
                }
                case 3: {
                    text3.setText(groups.get(i).getLongName()+": "+groups.get(i).getShortName()+" m");
                    break;
                }
                case 4: {
                    text4.setText(groups.get(i).getLongName()+": "+groups.get(i).getShortName()+" mm");
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flood, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flood_menu_regra: {
                params();
                break;
            }
            case R.id.flood_menu_local: {
                changeCity();
                break;
            }
            case R.id.flood_menu_chave: {
                changePassword();
                break;
            }
            case R.id.flood_menu_sair: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
