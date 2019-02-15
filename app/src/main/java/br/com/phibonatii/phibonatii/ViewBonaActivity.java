package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.phibonatii.phibonatii.adapter.MyBonasAdapter;
import br.com.phibonatii.phibonatii.model.Bona;

public class ViewBonaActivity extends AppCompatActivity {

    private String token;
    private Long bonaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bona);

        token = getIntent().getStringExtra("token");
        bonaId = getIntent().getLongExtra("bonaId", 0);

        WebClient webClient = new WebClient(this);
        webClient.viewBona(token, bonaId, new ResponseViewBona());
    }
}

class ResponseViewBona implements IResponseViewBona {
    public void onPostExecute(Context context, Bona bona, String serverError) {
        ViewBonaActivity app = (ViewBonaActivity) context;
        TextView campoNome = (TextView) app.findViewById(R.id.text_bona);

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            campoNome.setText(bona.getDescription());
        }
    }
}
