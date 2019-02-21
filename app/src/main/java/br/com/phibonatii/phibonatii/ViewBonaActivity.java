package br.com.phibonatii.phibonatii;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
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

    public void confirm(View view) {
        this.finish();
    }

}

class ResponseViewBona implements IResponseViewBona {
    public void onPostExecute(Context context, Bona bona, String serverError) {
        ViewBonaActivity app = (ViewBonaActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            TextView campoNome = (TextView) app.findViewById(R.id.text_description);
            campoNome.setText(bona.getDescription());

            byte[] byteArray2 = Base64.decode(bona.getPhoto(), Base64.DEFAULT);
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray2);
            Bitmap bitmap2 = BitmapFactory.decodeStream(arrayInputStream);
            ImageView imageView = (ImageView) app.findViewById(R.id.image_photo);
            imageView.setImageBitmap(bitmap2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView campoEspecificacao = (TextView) app.findViewById(R.id.text_specification);
            campoEspecificacao.setText(bona.getSpecification());
        }
    }
}
