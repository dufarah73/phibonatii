package br.com.phibonatii.phibonatii;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.phibonatii.phibonatii.model.Bona;

public class ViewBonaActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 103;
    private static final int CAMERA_REQUEST_CODE = 203;

    private String token;
    private Long bonaId;
    private String photo;

    public Bona bona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bona);

        token = getIntent().getStringExtra("token");
        bonaId = getIntent().getLongExtra("bonaId", 0);

        WebClient webClient = new WebClient(this);
        webClient.viewBona(token, bonaId, new ResponseViewBona());
    }

    private boolean toValidate() {
        if (photo == null) {
            Toast.makeText(this, "Falta foto de bona encontrado", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }
    public void confirm(View view) {
        if (this.bona.getMine()) {
            if (this.bona.getFoundNotConfirmed()) {
                this.findViewById(R.id.button_confirm).setEnabled(false);
                WebClient webClient = new WebClient(this);
                webClient.findBona(token, bonaId, "", new ResponseFindBona());
            } else {
                this.finish();
            }
        } else if (toValidate()) {
            this.findViewById(R.id.button_confirm).setEnabled(false);
            WebClient webClient = new WebClient(this);
            webClient.findBona(token, bonaId, photo, new ResponseFindBona());
        }
    }

    public void capturePhoto(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
//            Toast.makeText(this, "CAMERA - permissão já concedida", Toast.LENGTH_LONG).show();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "CAMERA - permissão concedida agora", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "CAMERA - permissão negada", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray2);
            Bitmap bitmap2 = BitmapFactory.decodeStream(arrayInputStream);

            photo = Base64.encodeToString(byteArray2, Base64.DEFAULT);

            ImageView imageView = (ImageView) this.findViewById(R.id.image_photo2);
            imageView.setImageBitmap(bitmap2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

}

class ResponseViewBona implements IResponseViewBona {
    public void onPostExecute(Context context, Bona bona, String serverError) {
        ViewBonaActivity app = (ViewBonaActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
        } else {
            app.bona = bona;

            TextView campoNome = (TextView) app.findViewById(R.id.text_description);
            campoNome.setText(bona.getDescription());

            TextView campoEspecificacao = (TextView) app.findViewById(R.id.text_specification);
            campoEspecificacao.setText(bona.getSpecification());

            byte[] byteArray2 = Base64.decode(bona.getPhoto(), Base64.DEFAULT);
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray2);
            Bitmap bitmap2 = BitmapFactory.decodeStream(arrayInputStream);
            ImageView imageView = (ImageView) app.findViewById(R.id.image_photo1);
            imageView.setImageBitmap(bitmap2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            if (bona.getMine()) {
                Button button = (Button) app.findViewById(R.id.button_capture_photo);
                button.setVisibility(View.INVISIBLE);

                byteArray2 = Base64.decode(bona.getPhotoAfterFound(), Base64.DEFAULT);
                arrayInputStream = new ByteArrayInputStream(byteArray2);
                bitmap2 = BitmapFactory.decodeStream(arrayInputStream);
                imageView = (ImageView) app.findViewById(R.id.image_photo2);
                imageView.setImageBitmap(bitmap2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}

class ResponseFindBona implements IResponseFindBona {
    public void onPostExecute(Context context, String serverError) {
        ViewBonaActivity app = (ViewBonaActivity) context;

        if (serverError != "") {
            Toast.makeText(context, serverError, Toast.LENGTH_LONG).show();
            Button button = (Button) app.findViewById(R.id.button_confirm);
            button.setEnabled(true);
        } else {
            app.finish();
        }
    }
}
