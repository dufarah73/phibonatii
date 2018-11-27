package br.com.phibonatii.phibonatii;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClientTask extends AsyncTask<Void, Void, String> {

    private WebClient webClient;
    private String action;
    private String jsonSent;
    private ProgressDialog dialog;

    public WebClientTask(WebClient webClient, String action, String json) {
        this.webClient = webClient;
        this.action = action;
        this.jsonSent = json;
    }

    private String post() {
        try {
            URL url = new URL("http://vpsw0088.publiccloud.com.br/phibonatii.dll/"+action);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(jsonSent);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            return scanner.next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{'Error':'Sem Conexão'}";
    }

    @Override
    protected void onPreExecute() {
//        Toast.makeText(webClient.context, jsonSent, Toast.LENGTH_LONG).show();
        dialog = ProgressDialog.show(webClient.context, "Aguarde", "Acessando servidor", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.post();
    }

    @Override
    protected void onPostExecute(String resposta) {
//        Toast.makeText(webClient.context, resposta, Toast.LENGTH_LONG).show();
        dialog.dismiss();
        webClient.onPostExecute(resposta);
    }
}
