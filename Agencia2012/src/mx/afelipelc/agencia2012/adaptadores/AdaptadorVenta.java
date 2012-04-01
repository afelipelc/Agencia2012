package mx.afelipelc.agencia2012.adaptadores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.Venta;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mx.afelipelc.agencia2012.models.VentaSimple;

public class AdaptadorVenta extends ArrayAdapter<VentaSimple> {

	
	Context context;
	VentaSimple[] ventas;
	static DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	//static Calendar calend = Calendar.getInstance();
	
	public AdaptadorVenta(Activity context, VentaSimple[] ventas) {
		super(context, R.layout.list_items_ventas, ventas);
		this.context = context;
        this.ventas = ventas;
	}
	
	 public View getView(int position, View convertView, ViewGroup parent) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View item = inflater.inflate(R.layout.list_items_ventas, null);
	        
	        TextView IdVentaLbl = (TextView) item.findViewById(R.id.IdVentaLbl);
	        IdVentaLbl.setText(String.valueOf(ventas[position].getIdVenta()));
	 
	        TextView serieautolbl = (TextView)item.findViewById(R.id.NoSerieAutoLbl);
	        serieautolbl.setText(ventas[position].getNoSerieAuto());
	        
	        TextView clientelbl = (TextView)item.findViewById(R.id.NombreClienteLbl);
	        clientelbl.setText(ventas[position].getCliente());
	 
	        TextView fechalbl = (TextView)item.findViewById(R.id.FechaVentaLbl);
	        //fechalbl.setText(String.valueOf(formato.format(ventas[position].getFecha())));
	        fechalbl.setText(ventas[position].getFecha());

	        return(item);
	 }

}
