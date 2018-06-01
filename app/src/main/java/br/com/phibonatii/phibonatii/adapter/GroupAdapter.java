package br.com.phibonatii.phibonatii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.phibonatii.phibonatii.R;
import br.com.phibonatii.phibonatii.model.Group;

public class GroupAdapter extends BaseAdapter {
    private final List<Group> objects;
    private final Context context;

    public GroupAdapter(Context context, List<Group> objects) {
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
        Group obj = (Group) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_group_item, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.group_shortname);
        campoNome.setText(obj.getShortName());
        campoNome.setTag(obj.getId());

        TextView campoDescricao = (TextView) view.findViewById(R.id.group_longname);
        campoDescricao.setText(obj.getLongName());

        return view;
    }
}
