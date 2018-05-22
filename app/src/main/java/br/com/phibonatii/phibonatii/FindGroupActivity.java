package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class FindGroupActivity extends AppCompatActivity {

    public EditText fieldSearchText;
    public String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_group);
    }

    private void getFields() {
        fieldSearchText = (EditText) this.findViewById(R.id.findgr_edit_searchtext);
    }
    private void getValues() {
        getFields();
        searchText = fieldSearchText.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        return true;
    }
    public void findGroup(View view) {
        if (toValidate()) {
            WebClient webClient = new WebClient(this);
            webClient.findGroup(searchText, new ResponseFindGroup());
        }
    }

    public void meetGroup(View view) {
    }
}

class ResponseFindGroup implements IResponseFindGroup {
    public void onPostExecute(Context context, List<String> groups, String serverError) {
        FindGroupActivity app = (FindGroupActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            Spinner spinner = (Spinner) app.findViewById(R.id.findgr_spinner_groups);
            spinner.setAdapter(new ArrayAdapter<String>(app, android.R.layout.simple_list_item_1, groups));
        }
    }
}
