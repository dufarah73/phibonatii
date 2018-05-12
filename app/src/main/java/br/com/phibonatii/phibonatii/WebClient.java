package br.com.phibonatii.phibonatii;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

interface IResponseLogin {
    public void onPostExecute(Context context, String token, String serverError);
}

interface IResponseJoin {
    public void onPostExecute(Context context, String serverError, List<String> nicknameErros, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros, List<String> passwordErros);
}

public class WebClient {

    public Context context;
    private IResponseLogin responseLogin;
    private IResponseJoin responseJoin;
    public String jsonReturned;

    public WebClient(Context context) {
        this.context = context;
    }

    public void login(String nickName, String password, IResponseLogin responseLogin) {
        this.responseLogin = responseLogin;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickName", nickName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "login", jsonObject.toString().replace("\"","'")).execute();
    }

    public void join(String nickName, String fullName, String dateBorn, String passAsking, String passAnswer, String password, IResponseJoin responseJoin) {
        this.responseJoin = responseJoin;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickName", nickName);
            jsonObject.put("fullName", fullName);
            jsonObject.put("dateBorn", dateBorn);
            jsonObject.put("passAsking", passAsking);
            jsonObject.put("passAnswer", passAnswer);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "join", jsonObject.toString().replace("'","*").replace("\"","'")).execute();
    }

    public void onPostExecute(String resposta) {
        jsonReturned = resposta;

        JSONObject jsonObject = null;

        String token = "";
        String error = "";
        JSONObject errors = null;

        List<String> nicknameErros = new ArrayList<String>();
        List<String> fullNameErros = new ArrayList<String>();
        List<String> dateBornErros = new ArrayList<String>();
        List<String> passAskingErros = new ArrayList<String> ();
        List<String> passAnswerErros = new ArrayList<String> ();
        List<String> passwordErros = new ArrayList<String> ();

        try {
            jsonObject = new JSONObject(this.jsonReturned);
        } catch (JSONException e) {
            error = e.toString();
        }

        if (jsonObject != null) {
            token = jsonObject.optString("Token");

            error = jsonObject.optString("Error");
            errors = jsonObject.optJSONObject("Errors");

            if (errors != null) {
                JSONArray arr;
                arr = errors.optJSONArray("Nickname");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        nicknameErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Full Name");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        fullNameErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Date Born");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        dateBornErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Pass Asking");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        passAskingErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Pass Answer");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        passAnswerErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Password");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        passwordErros.add(arr.optString(i));
                    }
                }
            }
        }

        if (responseLogin != null) {
            responseLogin.onPostExecute(context, token, error);
        }
        if (responseJoin != null) {
            responseJoin.onPostExecute(context, error, nicknameErros, fullNameErros, dateBornErros, passAskingErros, passAnswerErros, passwordErros);
        }
    }

}
