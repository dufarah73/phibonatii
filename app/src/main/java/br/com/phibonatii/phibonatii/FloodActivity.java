package br.com.phibonatii.phibonatii;

import android.content.Intent;
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
//    private List<Group> groups;

    private TextView textStatus;
    private ImageView imageStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood);

        token = getIntent().getStringExtra("token");
//        groups = (List<Group>) getIntent().getSerializableExtra("groups");

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flood, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flood_menu_regra: {
                changePassword();
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
