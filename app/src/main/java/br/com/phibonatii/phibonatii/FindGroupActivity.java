package br.com.phibonatii.phibonatii;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.phibonatii.phibonatii.adapter.GroupAdapter;
import br.com.phibonatii.phibonatii.model.Group;

public class FindGroupActivity extends AppCompatActivity {

    private String token;

    public EditText fieldSearchText;
    public String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_group);

        token = getIntent().getStringExtra("token");
    }

    private void getFields() {
        fieldSearchText = (EditText) this.findViewById(R.id.edit_searchtext);
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
        Spinner sp = (Spinner) this.findViewById(R.id.spinner_groups);
        TextView tx = (TextView) sp.getSelectedView().findViewById(R.id.group_shortname);

        WebClient webClient = new WebClient(this);
        webClient.meetGroup(token, (Long) tx.getTag(), tx.getText().toString(), new ResponseMeetGroup());
    }
}

class ResponseFindGroup implements IResponseFindGroup {
    public void onPostExecute(Context context, List<Group> groups, String serverError) {
        FindGroupActivity app = (FindGroupActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            Spinner spinner = (Spinner) app.findViewById(R.id.spinner_groups);
            spinner.setAdapter(new GroupAdapter(app, groups));
        }
    }
}

class ResponseMeetGroup implements IResponseMeetGroup {
    public void onPostExecute(Context context, String serverError) {
        FindGroupActivity app = (FindGroupActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            Spinner sp = (Spinner) app.findViewById(R.id.spinner_groups);
            TextView tx = (TextView) sp.getSelectedView().findViewById(R.id.group_shortname);

            app.getIntent().putExtra("groupid", (Long) tx.getTag());
            app.getIntent().putExtra("groupshortname", tx.getText().toString());
            app.setResult(Activity.RESULT_OK, app.getIntent());
            app.finish();
        }
    }
}
