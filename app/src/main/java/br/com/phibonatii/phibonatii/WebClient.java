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

interface IResponseNewGroup {
    public void onPostExecute(Context context, int groupId, String groupShortName, String serverError, List<String> shortNameErros, List<String> longNameErros);
}

interface IResponseFindGroup {
    public void onPostExecute(Context context, List<Integer> groupIds, List<String> groupNames, String serverError);
}

public class WebClient {

    public Context context;
    private IResponseLogin responseLogin;
    private IResponseJoin responseJoin;
    private IResponseNewGroup responseNewGroup;
    private IResponseFindGroup responseFindGroup;
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

    public void newGroup(String shortName, String longName, IResponseNewGroup responseNewGroup) {
        this.responseNewGroup = responseNewGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shortName", shortName);
            jsonObject.put("longName", longName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "newgroup", jsonObject.toString().replace("'","*").replace("\"","'")).execute();
    }

    public void findGroup(String searchText, IResponseFindGroup responseFindGroup) {
        this.responseFindGroup = responseFindGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchText", searchText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "findgroup", jsonObject.toString().replace("'","*").replace("\"","'")).execute();
    }

    public void onPostExecute(String resposta) {
        jsonReturned = resposta;

        JSONObject jsonObject = null;
        JSONArray arr;

        String token = "";
        int groupId = 0;
        String groupShortName = "";
        List<Integer> groupIds = new ArrayList<Integer>();
        List<String> groupNames = new ArrayList<String>();

        String error = "";
        JSONObject errors = null;

        List<String> nicknameErros = new ArrayList<String>();
        List<String> fullNameErros = new ArrayList<String>();
        List<String> dateBornErros = new ArrayList<String>();
        List<String> passAskingErros = new ArrayList<String> ();
        List<String> passAnswerErros = new ArrayList<String> ();
        List<String> passwordErros = new ArrayList<String> ();
        List<String> shortNameErros = new ArrayList<String>();
        List<String> longNameErros = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(this.jsonReturned);
        } catch (JSONException e) {
            error = e.toString();
        }

        if (jsonObject != null) {
            token = jsonObject.optString("Token");

            groupId = jsonObject.optInt("GroupID");
            groupShortName = jsonObject.optString("GroupShortName");

            arr = jsonObject.optJSONArray("GroupIDs");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    groupIds.add(arr.optInt(i));
                }
            }
            arr = jsonObject.optJSONArray("GroupNames");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    groupNames.add(arr.optString(i));
                }
            }

            error = jsonObject.optString("Error");
            errors = jsonObject.optJSONObject("Errors");

            if (errors != null) {
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
                arr = errors.optJSONArray("Short Name");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        shortNameErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Long Name");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        longNameErros.add(arr.optString(i));
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
        if (responseNewGroup != null) {
            responseNewGroup.onPostExecute(context, groupId, groupShortName, error, shortNameErros, longNameErros);
        }
        if (responseFindGroup != null) {
            responseFindGroup.onPostExecute(context, groupIds, groupNames, error);
        }
    }

}
