package br.com.phibonatii.phibonatii;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import br.com.phibonatii.phibonatii.adapter.BonaAdapter;
import br.com.phibonatii.phibonatii.adapter.RadarAdapter;
import br.com.phibonatii.phibonatii.adapter.RankingAdapter;
import br.com.phibonatii.phibonatii.model.Bona;
import br.com.phibonatii.phibonatii.model.Radar;
import br.com.phibonatii.phibonatii.model.Ranking;

public class MainActivity extends AppCompatActivity {

    private String token;
    private String[] groups;

    private ListView objectList;
    private List<Radar> radarList;
    private List<Ranking> rankingList;
    private List<Bona> bonaList;

    private Intent intentGoTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = getIntent().getStringExtra("token");
        groups = getIntent().getStringArrayExtra("groups");

        objectList = (ListView) findViewById(R.id.listview);

        radarList = new ArrayList<Radar>();
        radarList.add(new Radar(Long.valueOf(25),"7 Bona - 290 Tii","Num raio de 5 metros"));
        radarList.add(new Radar(Long.valueOf(26), "48 Bona - 7448 Tii", "Num raio de 8 metros"));
        radarList.add(new Radar(Long.valueOf(27), "70 Bona - 8299 Tii", "Num raio de 13 metros"));
        radarList.add(new Radar(Long.valueOf(28), "186 Bona - 18589 Tii", "Num raio de 21 metros"));
        radarList.add(new Radar(Long.valueOf(28), "357 Bona - 85009 Tii", "Num raio de 34 metros"));
        rankingList = new ArrayList<Ranking>();
        rankingList.add(new Ranking(Long.valueOf(35),"MAICON - 8300 Tii","Maicon da Siva Viana"));
        rankingList.add(new Ranking(Long.valueOf(36), "COX4893 - 743 Tii", "Eduardo Cox"));
        rankingList.add(new Ranking(Long.valueOf(37), "JAIR - 55 Tii", "José Antonio Imair Ramos"));
        bonaList = new ArrayList<Bona>();
        bonaList.add(new Bona(Long.valueOf(15),"Presente - 370 Tii","Presente para maria"));
        bonaList.add(new Bona(Long.valueOf(16), "Cigarro - 433 Tii", "Roxo"));
        bonaList.add(new Bona(Long.valueOf(17), "Jujuba - 155 Tii", "Pacote de jujuba sortida"));
        bonaList.add(new Bona(Long.valueOf(17), "Pacote - 42 Tii", "Embrulho de fogos"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"RADAR","RANKING","BONA"}));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TabLayout tb = (TabLayout) findViewById(R.id.tabs);
                int myTabPosition = tb.getSelectedTabPosition();
                int mySpinnerPosition = position;

                if (mySpinnerPosition == 0) {
                    displayListRadar(radarList);
                } else
                if (mySpinnerPosition == 1) {
                    displayListRanking(rankingList);
                } else {
                    displayListBona(bonaList);
                };

                Toast.makeText(tb.getContext(), "spi -> sp:"+String.valueOf(mySpinnerPosition)+",tb:"+String.valueOf(myTabPosition), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Spinner sp = (Spinner) findViewById(R.id.spinner);
                int mySpinnerPosition = sp.getSelectedItemPosition();
                int myTabPosition = tab.getPosition();
                Toast.makeText(sp.getContext(), "tab -> sp:"+String.valueOf(mySpinnerPosition)+",tb:"+String.valueOf(myTabPosition), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        if (groups != null) {
            for (int i = 1; i < groups.length; i += 2) {
                tabLayout.addTab(tabLayout.newTab().setText(groups[i]));
            }
        }
        tabLayout.addTab(tabLayout.newTab().setText("GLOBAL"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (intentGoTo != null) {
            String groupShortName = intentGoTo.getStringExtra("groupshortname");
            if (groupShortName != "") {
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.addTab(tabLayout.newTab().setText(groupShortName));
                intentGoTo.putExtra("groupshortname", "");
            }
        }
    }

    private void displayListRadar(List<Radar> objects) {
        RadarAdapter adapter = new RadarAdapter(this, objects);
        objectList.setAdapter(adapter);
    }

    private void displayListRanking(List<Ranking> objects) {
        RankingAdapter adapter = new RankingAdapter(this, objects);
        objectList.setAdapter(adapter);
    }

    private void displayListBona(List<Bona> objects) {
        BonaAdapter adapter = new BonaAdapter(this, objects);
        objectList.setAdapter(adapter);
    }

    public void newGroup() {
        intentGoTo = new Intent(this, NewGroupActivity.class);
        intentGoTo.putExtra("token", token);
        startActivity(intentGoTo);
    }

    public void findGroup() {
        intentGoTo = new Intent(this, FindGroupActivity.class);
        intentGoTo.putExtra("token", token);
        startActivity(intentGoTo);
    }

    public void leaveGroup() {
        intentGoTo = new Intent(this, FindGroupActivity.class);
        intentGoTo.putExtra("token", token);
        startActivity(intentGoTo);
    }

    public void changePassword() {
        intentGoTo = new Intent(this, ChangePasswordActivity.class);
        intentGoTo.putExtra("token", token);
        startActivity(intentGoTo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_new_group: {
                newGroup();
                break;
            }
            case R.id.main_menu_find_group: {
                findGroup();
                break;
            }
            case R.id.main_menu_leave_group: {
                leaveGroup();
                break;
            }
            case R.id.main_menu_change_password: {
                changePassword();
                break;
            }
            case R.id.main_menu_logout: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
