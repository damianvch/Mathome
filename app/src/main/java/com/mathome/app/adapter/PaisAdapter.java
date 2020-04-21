package com.mathome.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mathome.app.R;
import com.mathome.app.entity.Pais;

public class PaisAdapter extends BaseAdapter implements Filterable {
    private final LayoutInflater inflater;
    private List<Pais> listaPaisesOut;
    private List<Pais> listaPaisesIn;

    public PaisAdapter(Context context, List<Pais> listaPaises){
        listaPaisesOut = listaPaises;
        listaPaisesIn = listaPaises;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaPaisesOut.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaPaisesOut = (List<Pais>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Pais> FilteredArrList = new ArrayList<>();
                if (listaPaisesIn == null) {
                    listaPaisesIn = new ArrayList<>(listaPaisesOut);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = listaPaisesIn.size();
                    results.values = listaPaisesIn;
                } else {

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listaPaisesIn.size(); i++) {
                        String data = listaPaisesIn.get(i).getNombre();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(listaPaisesIn.get(i));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    @Override
    public Pais getItem(int position) {
        return listaPaisesOut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.pais_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtNombre.setText(listaPaisesOut.get(position).getNombre());
        holder.txtPrefijo.setText(listaPaisesOut.get(position).getPrefijo());
        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.txtNombre)
        TextView txtNombre;
        @BindView(R.id.txtPrefijo)
        TextView txtPrefijo;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
