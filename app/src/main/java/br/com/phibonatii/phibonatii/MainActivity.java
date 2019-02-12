package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import br.com.phibonatii.phibonatii.model.Group;
import br.com.phibonatii.phibonatii.model.Radar;
import br.com.phibonatii.phibonatii.model.Ranking;
import br.com.phibonatii.phibonatii.model.SingleLocalizator;

public class MainActivity extends AppCompatActivity {

    private String token;
    private List<Group> groups;
    private Long groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = getIntent().getStringExtra("token");
        groups = (List<Group>) getIntent().getSerializableExtra("groups");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"AROUND","RANKING","MY BONAS"}));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int mySpinnerPosition = position;
                TabLayout tb = (TabLayout) findViewById(R.id.tabs);
                TabLayout.Tab tab = tb.getTabAt(tb.getSelectedTabPosition());
                groupId = (Long) tab.getTag();
                if (mySpinnerPosition == 0) {
                    displayListRadar();
                } else
                if (mySpinnerPosition == 1) {
                    displayListRanking();
                } else {
                    displayListMyBonas();
                };
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
                groupId = (Long) tab.getTag();
                if (mySpinnerPosition == 0) {
                    displayListRadar();
                } else
                if (mySpinnerPosition == 1) {
                    displayListRanking();
                } else {
                    displayListMyBonas();
                };
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TabLayout.Tab tab;
        for (int i = 0 ; i < groups.size() ; i++) {
            tab = tabLayout.newTab();
            tab.setText(groups.get(i).getShortName());
            tab.setTag(groups.get(i).getId());
            tabLayout.addTab(tab);
        }
        tab = tabLayout.newTab();
        tab.setText("GLOBAL");
        tab.setTag(Long.valueOf(0));
        tabLayout.addTab(tab);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void displayListMyBonas() {
        WebClient webClient = new WebClient(this);
        webClient.myBonas(token, groupId, new ResponseMyBonas());
    }

    private void displayListRadar() {
        SingleLocalizator.requestSingleUpdate(this,
                new SingleLocalizator.LocationCallback() {
                    @Override public void onNewLocationAvailable(Context context, Location location) {
                        MainActivity app = (MainActivity) context;
//                        Toast.makeText(context, "GroupID:" + String.valueOf(app.groupId) + " Latitude:" + String.valueOf(location.getLatitude()) +" Longitude:" + String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
                        WebClient webClient = new WebClient(context);
                        webClient.radar(app.token, app.groupId, location.getLatitude(), location.getLongitude(), new ResponseRadar());
                    }
                });
    }

    private void displayListRanking() {
        WebClient webClient = new WebClient(this);
        webClient.ranking(token, groupId, new ResponseRanking());
    }

    public void newGroup() {
        Intent intent = new Intent(this, NewGroupActivity.class);
        intent.putExtra("token", token);
        startActivityForResult(intent, 1 /*New Group*/);
    }

    public void findGroup() {
        Intent intent = new Intent(this, FindGroupActivity.class);
        intent.putExtra("token", token);
        startActivityForResult(intent, 2 /*Find Group*/);
    }

    public void leaveGroup() {
        TabLayout tb = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tb.getTabAt(tb.getSelectedTabPosition());
        Long idGroup = (Long) tab.getTag();

        WebClient webClient = new WebClient(this);
        webClient.leaveGroup(token, idGroup, new ResponseLeaveGroup());
    }

    public void changePassword() {
        Intent intent = new Intent(this, SimpleChangePasswordActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void hideBona(View view) {
        Intent intent = new Intent(this, HideBonaActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Long groupId = data.getLongExtra("groupid", 0);
            String groupShortName = data.getStringExtra("groupshortname");
            if (groupShortName != "") {
                TabLayout tb = (TabLayout) findViewById(R.id.tabs);
                TabLayout.Tab tab;
                tab = tb.newTab();
                tab.setText(groupShortName);
                tab.setTag(groupId);
                tb.addTab(tab);
            }
        }
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

class ResponseLeaveGroup implements IResponseLeaveGroup {
    public void onPostExecute(Context context, String serverError) {
        MainActivity app = (MainActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            TabLayout tb = (TabLayout) app.findViewById(R.id.tabs);
            String s = tb.getTabAt(tb.getSelectedTabPosition()).getText().toString();
            tb.removeTabAt(tb.getSelectedTabPosition());
            Toast.makeText(context, "Grupo "+s+" Removido", Toast.LENGTH_LONG).show();
        }
    }
}

class ResponseMyBonas implements IResponseMyBonas {
    public void onPostExecute(Context context, List<Bona> bonas, String serverError) {
        MainActivity app = (MainActivity) context;
        ListView objectList = (ListView) app.findViewById(R.id.listview);

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            objectList.setAdapter(new BonaAdapter(app, bonas));
        }
    }
}

class ResponseRadar implements IResponseRadar {
    public void onPostExecute(Context context, List<Radar> radar, String serverError) {
        MainActivity app = (MainActivity) context;
        ListView objectList = (ListView) app.findViewById(R.id.listview);

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            objectList.setAdapter(new RadarAdapter(app, radar));
        }
    }
}

class ResponseRanking implements IResponseRanking {
    public void onPostExecute(Context context, List<Ranking> ranking, String serverError) {
        MainActivity app = (MainActivity) context;
        ListView objectList = (ListView) app.findViewById(R.id.listview);

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            objectList.setAdapter(new RankingAdapter(app, ranking));
        }
    }
}
