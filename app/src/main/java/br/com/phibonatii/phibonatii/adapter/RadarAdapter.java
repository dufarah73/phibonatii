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

public class RadarAdapter extends BaseAdapter {
    private final List<Bona> objects;
    private final Context context;

    public RadarAdapter(Context context, List<Bona> objects) {
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
            view = inflater.inflate(R.layout.list_radar_item, parent, false);
        }

        if (obj.getDistanceFromMe() <= 5) {
            view.setBackgroundColor(Color.parseColor("#FF4A40"));
        } else if (obj.getDistanceFromMe() <= 13) {
            view.setBackgroundColor(Color.parseColor("#FF9040"));
        } else if (obj.getDistanceFromMe() <= 34) {
            view.setBackgroundColor(Color.parseColor("#FFDC40"));
        } else if (obj.getDistanceFromMe() <= 89) {
            view.setBackgroundColor(Color.parseColor("#D2FF40"));
        } else if (obj.getDistanceFromMe() <= 233) {
            view.setBackgroundColor(Color.parseColor("#40FF9C"));
        } else if (obj.getDistanceFromMe() <= 610) {
            view.setBackgroundColor(Color.parseColor("#51E8FF"));
        } else if (obj.getDistanceFromMe() <= 1597) {
            view.setBackgroundColor(Color.parseColor("#D0D7FF"));
        }

        TextView campoNome = (TextView) view.findViewById(R.id.radar_name);
        campoNome.setText(obj.getDescription());

        TextView campoRange = (TextView) view.findViewById(R.id.radar_range);
        campoRange.setText(obj.getDistanceFromMe()+"m");

        TextView campoDescricao = (TextView) view.findViewById(R.id.radar_description);
        campoDescricao.setText(obj.getSpecification());

        return view;
    }
}
