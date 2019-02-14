package br.com.phibonatii.phibonatii;

import android.graphics.Color;
import android.widget.EditText;

import java.util.List;

import br.com.phibonatii.phibonatii.R;

public final class Utils {

    public static String putErrorOnField(List<String> erros, EditText field, String hint) {
        String msgErros = "";
        if (!erros.isEmpty()) {
            msgErros += hint + ": ";
            for (String s : erros) {
                msgErros = msgErros + s + "\t";
            }
            field.setBackgroundColor(Color.parseColor("#FF4081"));
        }
        return msgErros;
    }

    public static void cleanErrorOnField(EditText field) {
        field.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
}
