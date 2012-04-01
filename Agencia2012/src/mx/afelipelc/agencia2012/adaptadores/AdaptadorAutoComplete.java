package mx.afelipelc.agencia2012.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.ElementoSimple;

public class AdaptadorAutoComplete extends ArrayAdapter<ElementoSimple> implements Filterable{

    protected LayoutInflater inflador;
    //protected Filter filtro;

    //Context context;
    private  ElementoSimple[] clientes;

    public AdaptadorAutoComplete(Context context, int textViewResourceId, ElementoSimple[] Clientes) {
        super(context, textViewResourceId, Clientes);
        filtro = new SuggestionsFilter();
        inflador = LayoutInflater.from(context);
        //this.context = context;
        this.clientes = Clientes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null)
        {
            convertView  = inflador.inflate(R.layout.spinner_item, parent, false);
        }

        TextView Id = (TextView) convertView.findViewById(R.id.IdLbl);
        TextView Nombre = (TextView) convertView.findViewById(R.id.NombreLbl);

        Id.setText(String.valueOf(clientes[position].getId()));
        Nombre.setText(clientes[position].getNombre());

        return convertView;
    }

     public Filter getFiltro()
     {
         return filtro;
     }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                filterResults.count = getCount();
            }
            // do some other stuff
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            }
        }
    };


    private class SuggestionsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }
}
