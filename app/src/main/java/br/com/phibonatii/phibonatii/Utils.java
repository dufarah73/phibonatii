package br.com.phibonatii.phibonatii;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Field;
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
            field.setBackgroundColor(Color.parseColor("#B36F6F"));
        }
        return msgErros;
    }

    public static void cleanErrorOnField(EditText field) {
        field.setBackgroundResource(android.R.drawable.edit_text);
    }

    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
