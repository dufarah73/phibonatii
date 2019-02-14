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
import br.com.phibonatii.phibonatii.model.Phi;

public class RankingAdapter extends BaseAdapter {
    private final List<Phi> objects;
    private final Context context;

    public RankingAdapter(Context context, List<Phi> objects) {
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
        Phi obj = (Phi) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_ranking_item, parent, false);
        }

        if (obj.getMe()) {
            view.setBackgroundColor(Color.parseColor("#51E8FF"));
        }

        TextView campoNome = (TextView) view.findViewById(R.id.ranking_name);
        campoNome.setText(obj.getNickname());

        TextView campoDescricao = (TextView) view.findViewById(R.id.ranking_description);
        campoDescricao.setText(obj.getFullName());

        return view;
    }
}
