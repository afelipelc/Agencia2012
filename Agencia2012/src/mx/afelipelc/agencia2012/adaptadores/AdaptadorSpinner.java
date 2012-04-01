package mx.afelipelc.agencia2012.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.MarcaAuto;

public class AdaptadorSpinner extends ArrayAdapter<MarcaAuto>{
    Context context;
    private  MarcaAuto[] marcas;
    public AdaptadorSpinner(Context context, int textViewResourceId, MarcaAuto[] Marcas) {
        super(context, textViewResourceId, Marcas);
        this.context = context;
        this.marcas = Marcas;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View item = inflater.inflate(R.layout.list_items_clientes, null);
//
//        TextView lblTitulo = (TextView)item.findViewById(R.id.NombreLbl);
//        lblTitulo.setText(marcas[position].getNombre());
//
//        TextView lblId = (TextView)item.findViewById(R.id.IdLbl);
//        lblId.setText(String.valueOf(marcas[position].getIdMarca()));
//
//
//        return item;

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.txt01 = (TextView) convertView
                    .findViewById(R.id.NombreLbl);
            holder.txt02 = (TextView) convertView
                    .findViewById(R.id.IdLbl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txt01.setText(marcas[position].getNombre());
        holder.txt02.setText(String.valueOf(marcas[position].getIdMarca()));

        return  convertView;
    }

    class ViewHolder {
        TextView txt01;
        TextView txt02;


    }
}
