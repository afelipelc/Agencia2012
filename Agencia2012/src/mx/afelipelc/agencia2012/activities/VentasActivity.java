package mx.afelipelc.agencia2012.activities;

import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosVentas;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorVenta;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import mx.afelipelc.agencia2012.models.VentaSimple;

public class VentasActivity extends Activity {

	ListView listaventas;
	Button atrasbtn;
	ImageButton navautos;
	ImageButton navclientes;
	TextView titulonavbar;
	ImageButton ventasbtn;
	ImageButton opcionesbtn;

    AccesoDatosVentas accesodatosventas;
    VentaSimple[] ventas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Establecer el layout a mostrar
		setContentView(R.layout.ventas);
		
		
		listaventas = (ListView) findViewById(R.id.listaventas);
		
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		navautos = (ImageButton) findViewById(R.id.autosnavbtn);
		navclientes = (ImageButton) findViewById(R.id.clientesnavbtn);
		
		//Ocultar el boton Ventas y Opciones de la barra de navegacion
		ventasbtn = (ImageButton) findViewById(R.id.ventasnavbtn);
		ventasbtn.setVisibility(View.GONE);
		opcionesbtn = (ImageButton) findViewById(R.id.opcionesnavbtn);
		//opcionesbtn.setVisibility(View.GONE);
		registerForContextMenu(opcionesbtn);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		titulonavbar.setText("Ventas");

        accesodatosventas = new AccesoDatosVentas(this);

        try
        {
            ventas = accesodatosventas.ListaVentas();
            if(ventas!= null)
            {
                listaventas.setAdapter(new AdaptadorVenta(this,ventas));
            }else
                Toast.makeText(this, "La lista de ventas esta vacia, o no se pudo cargar la lista de ventas (verifique que la URL de los WS sea correcta).", Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
            Log.d("Error en VentasActivity", e.getMessage() + " >>>" + e.getStackTrace());
            Toast.makeText(this, "Ocurrio un error al cargar la lista de ventas.", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        //Acceder a los datos de ventas
        accesodatosventas = new AccesoDatosVentas(this);

		
		//Al seleccionar un elemento
		listaventas.setOnItemClickListener(new OnItemClickListener() {
		     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			       	//Al seleccionar
			     	  //Toast.makeText(parent.getContext(), "Seleccionado >>> " + position, position ).show();
			     	  //View item = listaventas.getChildAt(position);
			     	  	     	  
			     	  TextView idventa = (TextView) view.findViewById(R.id.IdVentaLbl);
//			     	  Toast.makeText(parent.getContext(), "Seleccionado >>> " + nombrec.getText(), position).show();
			     	  //Toast.makeText(parent.getContext(), "El ID de la venta seleccionada es >>> " + idventa.getText(), position).show();
			     	  
			     	  //Abrir el activity de Datos de la venta
			     	  Intent ventanadatos = new Intent(VentasActivity.this, DatosVentaActivity.class);	 		    
			 		    ventanadatos.putExtra("Id", idventa.getText());
			 		    startActivity(ventanadatos);
			       	}
			     });
		
		
		//Boton Clientes del Nav Bar
		navclientes.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaclientes = new Intent(VentasActivity.this, ClientesActivity.class);
			    startActivity(ventanaclientes);
			}
		});
		
		//Boton Autos del Nav Bar
		navautos.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaclientes = new Intent(VentasActivity.this, AutosActivity.class);
			    startActivity(ventanaclientes);
			}
		});		
		
		//Al presionar el boton atras
	     this.atrasbtn.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v)
	        {
	        finish();
	        }
	     });
	     
	        //mostrar men� contextual
	      //Boton opciones
	        opcionesbtn.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View v) {				
					v.showContextMenu();
				}
			});
	}
	
	@Override
    	protected void onRestart()
    	{
	        super.onRestart();
    	}
    
	//el menu contextual
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Opciones");
		menu.setHeaderIcon(R.drawable.options_small);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.opcionesmenu, menu);
		
		MenuItem opcion;
		opcion = menu.findItem(R.id.EditarOpt);
	   opcion.setVisible(false);
	   
		MenuItem opcionagregar;
		opcionagregar = menu.findItem(R.id.AgregarOpt);
		opcionagregar.setVisible(false);		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
//		Toast.makeText(super.getBaseContext(), "opcion seleccionada.", Toast.LENGTH_SHORT).show();
//		return true;
	switch(item.getItemId()) {
	case R.id.InicioOpt:
		//Toast.makeText(super.getBaseContext(), "Seleccionado >>> inicio ", Toast.LENGTH_SHORT).show();
		Intent ventanainicio = new Intent(VentasActivity.this, Home.class);
	    startActivity(ventanainicio);
		return true;
	case R.id.CancelarOpt:		
		return true;
		}
		return super.onContextItemSelected(item);
		}
}
