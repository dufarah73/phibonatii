package br.com.phibonatii.phibonatii.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.phibonatii.phibonatii.R;
import br.com.phibonatii.phibonatii.model.Bona;

public class MyBonasAdapter extends BaseAdapter {
    private final List<Bona> objects;
    private final Context context;

    public MyBonasAdapter(Context context, List<Bona> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bona obj = (Bona) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_bona_item, parent, false);
        }

        if (obj.getStillHidden()) {
            view.setBackgroundColor(Color.parseColor("#D0D7FF"));
            TextView campoEscondido = (TextView) view.findViewById(R.id.bona_hidden);
            campoEscondido.setText("Escondido");
        } else if (obj.getFoundNotConfirmed()) {
            view.setBackgroundColor(Color.parseColor("#FF9040"));
            TextView campoEscondido = (TextView) view.findViewById(R.id.bona_hidden);
            campoEscondido.setText("Encontrado");
        }

        TextView campoNome = (TextView) view.findViewById(R.id.bona_name);
        campoNome.setText(obj.getDescription());

        TextView campoDescricao = (TextView) view.findViewById(R.id.bona_description);
        campoDescricao.setText(obj.getSpecification());

        return view;
    }
}
