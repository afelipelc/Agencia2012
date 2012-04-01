package mx.afelipelc.agencia2012.adaptadores;

import android.content.Context;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.Auto;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorAuto extends ArrayAdapter<Auto> {

	private Context context;
	private Auto[] autos;
	public AdaptadorAuto(Context context, Auto[] autos) {
		super(context, R.layout.list_items_autos, autos);
		// TODO Auto-generated constructor stub
		this.context = context;
        this.autos = autos;
	}
	
	 public View getView(int position, View convertView, ViewGroup parent) {
	        //LayoutInflater inflater = context.getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View item = inflater.inflate(R.layout.list_items_autos, null);
	        
	        TextView IdAutoLbl = (TextView) item.findViewById(R.id.AutoIdLbl);
	        IdAutoLbl.setText(String.valueOf(autos[position].getId()));
	 
	        TextView serielbl = (TextView)item.findViewById(R.id.NoSerieAutoLbl);
	        serielbl.setText(autos[position].getNoSerie());
	        
	        TextView marcalbl = (TextView)item.findViewById(R.id.MarcaAutoLbl);
	        marcalbl.setText(autos[position].getMarca().getNombre());
	 
	        TextView modelolbl = (TextView)item.findViewById(R.id.ModeloAutoLbl);
	        modelolbl.setText(String.valueOf(autos[position].getModelo()));
	        
	        TextView anolbl = (TextView)item.findViewById(R.id.AnoAutoLbl);
	        anolbl.setText(String.valueOf(autos[position].getAnio()));
	 
	        return(item);
	 }
}
