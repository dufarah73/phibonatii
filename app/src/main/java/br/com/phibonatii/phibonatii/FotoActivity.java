package br.com.phibonatii.phibonatii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class FotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        ImageView imageView = (ImageView) this.findViewById(R.id.image_photo);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Integer doadorId = Integer.valueOf(getIntent().getStringExtra("doadorId"));
        if (doadorId == 37) {
            imageView.setImageResource(R.drawable.im_cpf12591544409);
        } else
        if (doadorId == 38) {
            imageView.setImageResource(R.drawable.im_cpf85643750449);
        }
    }
}
