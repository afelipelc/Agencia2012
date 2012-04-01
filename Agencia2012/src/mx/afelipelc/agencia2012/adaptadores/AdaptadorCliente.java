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
public class AdaptadorCliente extends ArrayAdapter<ElementoSimple> {

	Context context;
	ElementoSimple[] clientes;

	//El Constructor ahora recibe la lista de clientes que deberá
	//adaptar al ListView, ya no utiza datos estáticos
	public AdaptadorCliente(Context context, ElementoSimple[] clientes) {
		super(context, R.layout.list_items_clientes, clientes);
		this.context = context;
		this.clientes = clientes;
		
	}
	
	 public View getView(int position, View convertView, ViewGroup parent) {
	        //LayoutInflater inflater = context.getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View item = inflater.inflate(R.layout.list_items_clientes, null);
	 
	        TextView lblTitulo = (TextView)item.findViewById(R.id.NombreLbl);
	        lblTitulo.setText(clientes[position].getNombre());
	 
	        TextView lblId = (TextView)item.findViewById(R.id.IdLbl);
	        lblId.setText(String.valueOf(clientes[position].getId()));
	 
	        return(item);
	 }
}