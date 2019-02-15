package br.com.phibonatii.phibonatii;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.List;

public class HideBonaActivity extends AppCompatActivity {

    private static final String UPLOAD_URL = "http://192.168.94.1/AndroidImageUpload/upload.php";
    private static final int CODIGO_IMAGEM = 345;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 103;
    private static final int CAMERA_REQUEST_CODE = 203;

    private String token;

    public EditText fieldDenomination;
    public EditText fieldSpecification;
    public EditText fieldHowMuch;

    private String photo, lat, lng;

    public String denomination;
    public String specification;
    public String howMuch;

    private LocalizationFragment.LocationCallback callbackLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_bona);
/*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 456);
        }
*/
        callbackLocal = new LocalizationFragment.LocationCallback() {
            @Override public void onNewLocationAvailable(Activity activity, Location location) {
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
//            Toast.makeText(this, "LOCATION - permissão já concedida", Toast.LENGTH_LONG).show();
            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMan.beginTransaction();
            LocalizationFragment fragLocal = new LocalizationFragment();
            fragLocal.SetLocalizationFragment(this, callbackLocal);
            fragTrans.replace(R.id.frame_localization, fragLocal);
            fragTrans.commit();
        }

        token = getIntent().getStringExtra("token");
    }
/*
    public void uploadMultipart() {
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(caminhoFoto, "image")
                    .addParameter("denomination", denomination)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); // Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
*/
/*
    public void getCompressed() {
        File cacheDir = this.getExternalCacheDir();
        if(cacheDir == null)
            cacheDir = this.getCacheDir();

        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
        File root = new File(rootDir);

        if(!root.exists())
            root.mkdirs();

        Bitmap bitmap = decodeImageFromFiles(caminhoFoto, 300,300);

        java.util.Date today = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss");
        String childFile = SDF.format(today) + ".jpg";
        File compressed = new File(root, childFile);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(compressed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        caminhoFotoP = rootDir + "/" + childFile;
    }
    public Bitmap decodeImageFromFiles(String path, int width, int height) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, outOptions);
    }
*/
/*
    public void imageToBase64() {
        try {
            InputStream inputStream = new FileInputStream(caminhoFotoP); // You can get an inputStream using any IO API
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            photo = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
*/

/*
    public void capturePhoto(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione Foto"), CODIGO_IMAGEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_IMAGEM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            Cursor cursor = getContentResolver().query(filePath, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID + " = ? ", new String[]{document_id},null);
            cursor.moveToFirst();
            caminhoFoto = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView imageView = (ImageView) this.findViewById(R.id.formulario_foto);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/

    public void capturePhoto(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
//            Toast.makeText(this, "CAMERA - permissão já concedida", Toast.LENGTH_LONG).show();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
/*
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
*/
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
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "LOCATION - permissão concedida agora", Toast.LENGTH_LONG).show();
                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMan.beginTransaction();
                LocalizationFragment fragLocal = new LocalizationFragment();
                fragLocal.SetLocalizationFragment(this, callbackLocal);
                fragTrans.replace(R.id.frame_localization, fragLocal);
                fragTrans.commit();
            } else {
                Toast.makeText(this, "LOCATION - permissão negada", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
/*
            int byteSize = bitmap1.getRowBytes() * bitmap1.getHeight();
            ByteBuffer byteBuffer = ByteBuffer.allocate(byteSize);
            bitmap1.copyPixelsToBuffer(byteBuffer);
            byte[] byteArray1 = byteBuffer.array();
*/
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray2);
            Bitmap bitmap2 = BitmapFactory.decodeStream(arrayInputStream);

            photo = Base64.encodeToString(byteArray2, Base64.DEFAULT);

            ImageView imageView = (ImageView) this.findViewById(R.id.image_photo);
            imageView.setImageBitmap(bitmap2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
/*
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (caminhoFoto != null) {
                    getCompressed();
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFotoP);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    ImageView campoFoto = (ImageView) this.findViewById(R.id.formulario_foto);
                    campoFoto.setImageBitmap(bitmapReduzido);
                    campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageToBase64();
                    TextView txt = (TextView) this.findViewById(R.id.edit_specification);
                    txt.setText(caminhoFoto + "**" + caminhoFotoP + "**" + String.valueOf(photo.length()));
                }
            }
        }
*/
    }

    private void getFields() {
        fieldDenomination = (EditText) this.findViewById(R.id.edit_denomination);
        fieldSpecification = (EditText) this.findViewById(R.id.edit_specification);
        fieldHowMuch = (EditText) this.findViewById(R.id.edit_howmuch);

        Utils.cleanErrorOnField(fieldDenomination);
        Utils.cleanErrorOnField(fieldSpecification);
        Utils.cleanErrorOnField(fieldHowMuch);
    }
    private void getValues() {
        getFields();
        denomination = fieldDenomination.getText().toString();
        specification = fieldSpecification.getText().toString();
        howMuch = fieldHowMuch.getText().toString();
    }
    private boolean toValidate() {
        getValues();
        if (photo == null) {
            Toast.makeText(this, "Falta foto", Toast.LENGTH_LONG).show();
        } else {
            if ((lat == null) || (lng == null)) {
                Toast.makeText(this, "Falta localização", Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }
        return false;
    }
    public void hideBona(View view) {
        if (toValidate()) {
            Button button = (Button) this.findViewById(R.id.button_hidebona);
            button.setEnabled(false);
            WebClient webClient = new WebClient(this);
            webClient.hideBona(token, denomination, specification, lat, lng, photo, howMuch, new ResponseHideBona());
        }
    }
}

class ResponseHideBona implements IResponseHideBona {
    public void onPostExecute(Context context, String serverError, List<String> denominationErros, List<String> specificationErros, List<String> howMuchErros) {
        String msgErros = "";
        HideBonaActivity app = (HideBonaActivity) context;

        if (serverError != "") {
            msgErros = "Erro no servidor:" + "\t" + serverError + "\t";
        } else {
            msgErros += Utils.putErrorOnField(denominationErros, app.fieldDenomination, app.getResources().getString(R.string.hint_denomination));
            msgErros += Utils.putErrorOnField(specificationErros, app.fieldSpecification, app.getResources().getString(R.string.hint_specification));
            msgErros += Utils.putErrorOnField(howMuchErros, app.fieldHowMuch, app.getResources().getString(R.string.hint_howmuch));
            if (msgErros != "") {
                msgErros = "Problema de preenchimento das informações:" + "\t" + msgErros;
            }
        }

        if (msgErros != "") {
            Toast.makeText(context, msgErros, Toast.LENGTH_LONG).show();
            Button button = (Button) app.findViewById(R.id.button_hidebona);
            button.setEnabled(true);
        } else {
            app.finish();
        }
    }
}
