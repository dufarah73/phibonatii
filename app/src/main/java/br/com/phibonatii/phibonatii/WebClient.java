package br.com.phibonatii.phibonatii;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

import br.com.phibonatii.phibonatii.model.Group;

interface IResponseLogin {
    public void onPostExecute(Context context, String token, List<Group> groups, String serverError);
}

interface IResponseJoin {
    public void onPostExecute(Context context, String serverError, List<String> nicknameErros, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros, List<String> passwordErros);
}

interface IResponseChangePassword {
    public void onPostExecute(Context context, String serverError, List<String> newPasswordErros);
}

interface IResponseRetrievePassAsking {
    public void onPostExecute(Context context, String passAsking, String serverError);
}

interface IResponseNewGroup {
    public void onPostExecute(Context context, List<Group> groups, String serverError, List<String> shortNameErros, List<String> longNameErros);
}

interface IResponseFindGroup {
    public void onPostExecute(Context context, List<Group> groups, String serverError);
}

interface IResponseMeetGroup {
    public void onPostExecute(Context context, String serverError);
}

interface IResponseLeaveGroup {
    public void onPostExecute(Context context, String serverError);
}

interface IResponseHideBona {
    public void onPostExecute(Context context, String serverError);
}

interface IResponseParams {
    public void onPostExecute(Context context, List<Group> groups, String serverError, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros);
}

public class WebClient {

    public Context context;
    private IResponseLogin responseLogin;
    private IResponseJoin responseJoin;
    private IResponseChangePassword responseChangePassword;
    private IResponseRetrievePassAsking responseRetrievePassAsking;
    private IResponseNewGroup responseNewGroup;
    private IResponseFindGroup responseFindGroup;
    private IResponseMeetGroup responseMeetGroup;
    private IResponseLeaveGroup responseLeaveGroup;
    private IResponseHideBona responseHideBona;
    private IResponseParams responseParams;

    public String jsonReturned;

    public WebClient(Context context) {
        this.context = context;
    }

    private String JSONObjectToString(JSONObject jsonObject) {
        return jsonObject.toString().replace("\"","%22").replace("+","%2B").replace("=","%3D").replace(":","%3A").replace(",","%2C").replace(" ","%20");
//        return jsonObject.toString().replace("'","*").replace("\"","'");
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
        new WebClientTask(this, "login", JSONObjectToString(jsonObject)).execute();
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
        new WebClientTask(this, "join", JSONObjectToString(jsonObject)).execute();
    }

    public void changePassword(String token, String nickName, String fullName, String dateBorn, String passAnswer, String currentPassword, String newPassword, IResponseChangePassword responseChangePassword) {
        this.responseChangePassword = responseChangePassword;
        JSONObject jsonObject = new JSONObject();
        try {
            if (token != "") {
                jsonObject.put("token", token);
            }
            jsonObject.put("nickName", nickName);
            jsonObject.put("fullName", fullName);
            jsonObject.put("dateBorn", dateBorn);
            jsonObject.put("passAnswer", passAnswer);
            jsonObject.put("currentPassword", currentPassword);
            jsonObject.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "changepassword", JSONObjectToString(jsonObject)).execute();
    }

    public void retrivePassAsking(String token, String nickName, String fullName, String dateBorn, IResponseRetrievePassAsking responseRetrievePassAsking) {
        this.responseRetrievePassAsking = responseRetrievePassAsking;
        JSONObject jsonObject = new JSONObject();
        try {
            if (token != "") {
                jsonObject.put("token", token);
            }
            jsonObject.put("nickName", nickName);
            jsonObject.put("fullName", fullName);
            jsonObject.put("dateBorn", dateBorn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "retrievepassasking", JSONObjectToString(jsonObject)).execute();
    }

    public void newGroup(String token, String shortName, String longName, IResponseNewGroup responseNewGroup) {
        this.responseNewGroup = responseNewGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("shortName", shortName);
            jsonObject.put("longName", longName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "newgroup", JSONObjectToString(jsonObject)).execute();
    }

    public void findGroup(String searchText, IResponseFindGroup responseFindGroup) {
        this.responseFindGroup = responseFindGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchText", searchText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "findgroup", JSONObjectToString(jsonObject)).execute();
    }

    public void meetGroup(String token, Long groupId, String groupShortName, IResponseMeetGroup responseMeetGroup) {
        this.responseMeetGroup = responseMeetGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("groupId", groupId);
            jsonObject.put("groupShortName", groupShortName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "meetgroup", JSONObjectToString(jsonObject)).execute();
    }

    public void leaveGroup(String token, Long groupId, IResponseLeaveGroup responseLeaveGroup) {
        this.responseLeaveGroup = responseLeaveGroup;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("groupId", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "leavegroup", JSONObjectToString(jsonObject)).execute();
    }

    public void hideBona(String token, String denomination, String specification, String lat, String lng, String photo, String howmuch, IResponseHideBona responseHideBona) {
        this.responseHideBona = responseHideBona;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("denomination", denomination);
            jsonObject.put("specification", specification);
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
            jsonObject.put("photo", photo);
            jsonObject.put("howmuch", howmuch);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "hidebona", JSONObjectToString(jsonObject)).execute();
    }

    public void params(String token, String previsaoChuva, String alturaMare, String nivelRio, String indicePluviometrico, IResponseParams responseParams) {
        this.responseParams = responseParams;
        JSONObject jsonObject = new JSONObject();
        try {
            if (token != "") {
                jsonObject.put("token", token);
            }
            jsonObject.put("previsaoChuva", previsaoChuva);
            jsonObject.put("alturaMare", alturaMare);
            jsonObject.put("nivelRio", nivelRio);
            jsonObject.put("indicePluviometrico", indicePluviometrico);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "params", JSONObjectToString(jsonObject)).execute();
    }

    public void onPostExecute(String resposta) {
        jsonReturned = resposta;

        JSONObject jsonObject = null;
        JSONArray arr;

        String token = "";
        List<Group> groups = new ArrayList<Group>();
        String passAsking = "";

        String error = "";
        JSONObject errors = null;

        List<String> nicknameErros = new ArrayList<String>();
        List<String> fullNameErros = new ArrayList<String>();
        List<String> dateBornErros = new ArrayList<String>();
        List<String> passAskingErros = new ArrayList<String> ();
        List<String> passAnswerErros = new ArrayList<String> ();
        List<String> passwordErros = new ArrayList<String> ();
        List<String> newPasswordErros = new ArrayList<String> ();
        List<String> shortNameErros = new ArrayList<String>();
        List<String> longNameErros = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(this.jsonReturned);
        } catch (JSONException e) {
            error = e.toString();
        }

        if (jsonObject != null) {
            token = jsonObject.optString("Token");

            arr = jsonObject.optJSONArray("Groups");
            if (arr != null) {
                for (int i = 2; i < arr.length(); i += 3) {
                    groups.add(new Group(arr.optLong(i-2), arr.optString(i-1), arr.optString(i)));
                }
            }

            passAsking = jsonObject.optString("PassAsking");

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
                arr = errors.optJSONArray("New Password");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        newPasswordErros.add(arr.optString(i));
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
            responseLogin.onPostExecute(context, token, groups, error);
        }
        if (responseJoin != null) {
            responseJoin.onPostExecute(context, error, nicknameErros, fullNameErros, dateBornErros, passAskingErros, passAnswerErros, passwordErros);
        }
        if (responseChangePassword != null) {
            responseChangePassword.onPostExecute(context, error, newPasswordErros);
        }
        if (responseRetrievePassAsking != null) {
            responseRetrievePassAsking.onPostExecute(context, passAsking, error);
        }
        if (responseNewGroup != null) {
            responseNewGroup.onPostExecute(context, groups, error, shortNameErros, longNameErros);
        }
        if (responseFindGroup != null) {
            responseFindGroup.onPostExecute(context, groups, error);
        }
        if (responseMeetGroup != null) {
            responseMeetGroup.onPostExecute(context, error);
        }
        if (responseLeaveGroup != null) {
            responseLeaveGroup.onPostExecute(context, error);
        }
        if (responseHideBona != null) {
            responseHideBona.onPostExecute(context, error);
        }
        if (responseParams != null) {
            responseParams.onPostExecute(context, groups, error, fullNameErros, dateBornErros, passAskingErros, passAnswerErros);
        }
    }

}
