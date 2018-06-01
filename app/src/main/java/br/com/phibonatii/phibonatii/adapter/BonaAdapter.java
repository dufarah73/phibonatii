package br.com.phibonatii.phibonatii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.phibonatii.phibonatii.R;
import br.com.phibonatii.phibonatii.model.Bona;

public class BonaAdapter extends BaseAdapter {
    private final List<Bona> objects;
    private final Context context;

    public BonaAdapter(Context context, List<Bona> objects) {
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

        TextView campoNome = (TextView) view.findViewById(R.id.bona_name);
        campoNome.setText(obj.getName());

        TextView campoDescricao = (TextView) view.findViewById(R.id.bona_description);
        campoDescricao.setText(obj.getDescription());

        return view;
    }
}
