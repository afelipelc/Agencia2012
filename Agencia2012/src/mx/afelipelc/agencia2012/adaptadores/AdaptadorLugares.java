package mx.afelipelc.agencia2012.adaptadores;

import android.content.Context;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.ElementoSimple;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorLugares extends ArrayAdapter<ElementoSimple> {
	
	Context context;
	ElementoSimple[] lugares;
	public AdaptadorLugares(Context context, ElementoSimple[] lugares) {
		super(context, R.layout.list_item_lugares, lugares);
		this.context = context;
		this.lugares = lugares;
	}

	 public View getView(int position, View convertView, ViewGroup parent) {
	        //LayoutInflater inflater = context.getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View item = inflater.inflate(R.layout.list_item_lugares, null);
	        //Inicio, mostrar datos en cada elemento del layout
	        TextView lblnombre = (TextView)item.findViewById(R.id.NombreLugarLbl);
	        lblnombre.setText(lugares[position].getNombre());
	 
	        TextView lblId = (TextView)item.findViewById(R.id.IdLugarLbl);
	        lblId.setText(String.valueOf(lugares[position].getId()));
	        //En este apartado se mandan a mostrar los datos en cada elemento
	        //Fin, mostrar datos en cada elemento del layout
	 
	        return(item);
	 }
	
//	public static Lugar[] datos = new Lugar[]{
//		new Lugar(1,"Lugar 1"),
//		new Lugar(2,"Lugar 2"),
//		new Lugar(4,"Lugar 4"),
//		new Lugar(6,"Lugar 6"),
//		new Lugar(9,"Lugar 9")
//	};
}

