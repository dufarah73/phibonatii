package br.com.phibonatii.phibonatii;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

import br.com.phibonatii.phibonatii.model.Group;
import br.com.phibonatii.phibonatii.model.Bona;
import br.com.phibonatii.phibonatii.model.Phi;

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
    public void onPostExecute(Context context, String serverError, List<String> denominationErros, List<String> specificationErros, List<String> howMuchErros);
}

interface IResponseFindBona {
    public void onPostExecute(Context context, String serverError);
}

interface IResponseViewBona {
    public void onPostExecute(Context context, Bona bona, String serverError);
}

interface IResponseMyBonas {
    public void onPostExecute(Context context, List<Bona> bonas, String serverError);
}

interface IResponseRadar {
    public void onPostExecute(Context context, List<Bona> bonas, String serverError);
}

interface IResponseRanking {
    public void onPostExecute(Context context, List<Phi> phis, String serverError);
}

interface IResponseParams {
    public void onPostExecute(Context context, List<Group> groups, String serverError, List<String> fullNameErros, List<String> dateBornErros, List<String> passAskingErros, List<String> passAnswerErros);
}

interface IResponseDoadores {
    public void onPostExecute(Context context, List<Phi> doadores, String serverError);
}

interface IResponseDoador {
    public void onPostExecute(Context context, String doadorId, String qdtParentes, String serverError, List<String> nomeErros, List<String> rgErros, List<String> cpfErros, List<String> nascimentoErros, List<String> naturalidadeErros);
}

interface IResponseParente {
    public void onPostExecute(Context context, String serverError, List<String> nomeErros, List<String> parentescoErros, List<String> enderecoErros, List<String> contatoErros);
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
    private IResponseFindBona responseFindBona;
    private IResponseViewBona responseViewBona;
    private IResponseMyBonas responseMyBonas;
    private IResponseRadar responseRadar;
    private IResponseRanking responseRanking;
    private IResponseParams responseParams;
    private IResponseDoadores responseDoadores;
    private IResponseDoador responseDoador;
    private IResponseParente responseParente;

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

    public void findBona(String token, Long bonaId, String photo, IResponseFindBona responseFindBona) {
        this.responseFindBona = responseFindBona;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("bonaId", bonaId);
            jsonObject.put("photo", photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "findbona", JSONObjectToString(jsonObject)).execute();
    }

    public void viewBona(String token, Long bonaId, IResponseViewBona responseViewBona) {
        this.responseViewBona = responseViewBona;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("bonaId", bonaId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "viewbona", JSONObjectToString(jsonObject)).execute();
    }

    public void myBonas(String token, Long groupId, IResponseMyBonas responseMyBonas) {
        this.responseMyBonas = responseMyBonas;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("groupId", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "mybonas", JSONObjectToString(jsonObject)).execute();
    }

    public void radar(String token, Long groupId, double lat, double lng, IResponseRadar responseRadar) {
        this.responseRadar = responseRadar;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("groupId", groupId);
            jsonObject.put("lat", String.valueOf(lat));
            jsonObject.put("lng", String.valueOf(lng));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "radar", JSONObjectToString(jsonObject)).execute();
    }

    public void ranking(String token, Long groupId, IResponseRanking responseRanking) {
        this.responseRanking = responseRanking;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            jsonObject.put("groupId", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "ranking", JSONObjectToString(jsonObject)).execute();
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

    public void doadores(String token, Boolean certificado, IResponseDoadores responseDoadores) {
        this.responseDoadores = responseDoadores;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
            if (certificado) {
                jsonObject.put("certificado", 1);

            } else {
                jsonObject.put("certificado", 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "doadores", JSONObjectToString(jsonObject)).execute();
    }

    public void doador(Boolean cadastrado, String nome, String rg, String cpf, String nascimento, String naturalidade, IResponseDoador responseDoador) {
        this.responseDoador = responseDoador;
        JSONObject jsonObject = new JSONObject();
        try {
            if (cadastrado) {
                jsonObject.put("cadastrado", 1);

            } else {
                jsonObject.put("cadastrado", 0);
            }
            jsonObject.put("nome", nome);
            jsonObject.put("rg", rg);
            jsonObject.put("cpf", cpf);
            jsonObject.put("nascimento", nascimento);
            jsonObject.put("naturalidade", naturalidade);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "doador", JSONObjectToString(jsonObject)).execute();
    }

    public void parente(String nome, String parentesco, String endereco, String contato, String doadorId, IResponseParente responseParente) {
        this.responseParente = responseParente;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome", nome);
            jsonObject.put("parentesco", parentesco);
            jsonObject.put("endereco", endereco);
            jsonObject.put("contato", contato);
            jsonObject.put("doadorId", doadorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebClientTask(this, "parente", JSONObjectToString(jsonObject)).execute();
    }

    public void onPostExecute(String resposta) {
        jsonReturned = resposta;

        JSONObject jsonObject = null;
        JSONArray arr;

        String token = "";
        Bona bona = null;
        List<Group> groups = new ArrayList<Group>();
        List<Bona> bonas = new ArrayList<Bona>();
        List<Phi> phis = new ArrayList<Phi>();
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
        List<String> denominationErros = new ArrayList<String>();
        List<String> specificationErros = new ArrayList<String>();
        List<String> howMuchErros = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(this.jsonReturned);
        } catch (JSONException e) {
            error = e.toString();
        }

        if (jsonObject != null) {
            token = jsonObject.optString("Token");

            arr = jsonObject.optJSONArray("Bona");
            if (arr != null) {
                bona = new Bona(arr.optLong(0), arr.optString(1), arr.optString(2));
                bona.setHowMuch(Long.valueOf(arr.optString(3)));
                bona.setPhoto(arr.optString(4));
                bona.setPhotoAfterFound(arr.optString(5));
                bona.setMine(Boolean.valueOf(arr.optString(6)));
                bona.setStillHidden(Boolean.valueOf(arr.optString(7)));
                bona.setFoundNotConfirmed(Boolean.valueOf(arr.optString(8)));
            }

            arr = jsonObject.optJSONArray("Groups");
            if (arr != null) {
                for (int i = 2; i < arr.length(); i += 3) {
                    groups.add(new Group(arr.optLong(i-2), arr.optString(i-1), arr.optString(i)));
                }
            }

            arr = jsonObject.optJSONArray("MyBonas");
            if (arr != null) {
                for (int i = 4; i < arr.length(); i += 5) {
                    bonas.add((new Bona(arr.optLong(i-4), arr.optString(i-3), arr.optString(i-2))));
                    bonas.get(bonas.size()-1).setStillHidden(Boolean.valueOf(arr.optString(i-1)));
                    bonas.get(bonas.size()-1).setFoundNotConfirmed(Boolean.valueOf(arr.optString(i)));
                }
            }

            arr = jsonObject.optJSONArray("Radar");
            if (arr != null) {
                for (int i = 3; i < arr.length(); i += 4) {
                    bonas.add((new Bona(arr.optLong(i-3), arr.optString(i-2), arr.optString(i-1))));
                    bonas.get(bonas.size()-1).setDistanceFromMe(Long.valueOf(arr.optString(i)));
                }
            }

            arr = jsonObject.optJSONArray("Ranking");
            if (arr != null) {
                for (int i = 3; i < arr.length(); i += 4) {
                    phis.add((new Phi(arr.optLong(i-3), arr.optString(i-2), arr.optString(i-1))));
                    phis.get(phis.size()-1).setMe(Boolean.valueOf(arr.optString(i)));
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
                arr = errors.optJSONArray("Denomination");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        denominationErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("Specification");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        specificationErros.add(arr.optString(i));
                    }
                }
                arr = errors.optJSONArray("How Much");
                if (arr != null) {
                    for (int i = 0; i < arr.length(); i++) {
                        howMuchErros.add(arr.optString(i));
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
            responseHideBona.onPostExecute(context, error, denominationErros, specificationErros, howMuchErros);
        }
        if (responseFindBona != null) {
            responseFindBona.onPostExecute(context, error);
        }
        if (responseViewBona != null) {
            responseViewBona.onPostExecute(context, bona, error);
        }
        if (responseMyBonas != null) {
            responseMyBonas.onPostExecute(context, bonas, error);
        }
        if (responseRadar != null) {
            responseRadar.onPostExecute(context, bonas, error);
        }
        if (responseRanking != null) {
            responseRanking.onPostExecute(context, phis, error);
        }
        if (responseParams != null) {
            responseParams.onPostExecute(context, groups, error, fullNameErros, dateBornErros, passAskingErros, passAnswerErros);
        }
        if (responseDoadores != null) {
            responseDoadores.onPostExecute(context, phis, error);
        }
        if (responseDoador != null) {
            responseDoador.onPostExecute(context, token, passAsking, error, fullNameErros, passAskingErros, passAnswerErros, dateBornErros, longNameErros);
        }
        if (responseParente != null) {
            responseParente.onPostExecute(context, error, fullNameErros, longNameErros, passAskingErros, passAnswerErros);
        }
    }

}
