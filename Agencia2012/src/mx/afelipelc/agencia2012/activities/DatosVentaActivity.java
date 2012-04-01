package mx.afelipelc.agencia2012.activities;

import android.widget.Toast;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosVentas;
import mx.afelipelc.agencia2012.models.Venta;

import java.text.DecimalFormat;

public class DatosVentaActivity extends Activity {

	Button atrasbtn;
	
	TextView idventa;
	TextView fechaventa;
	TextView montopagado;
	
	TextView noserie;
	TextView marca;
	TextView modelo;
	TextView anio;
	TextView color;
	TextView precio;
	
	TextView municipio;
	TextView estado;
	TextView telefono;
	TextView email;
	
	TextView nombretxt;
	TextView edadtxt;
	TextView domiciliotxt;	
	TextView localidadtxt;
	
	//Barras de navegacion
	ImageButton navautos;
	ImageButton navventas;
	ImageButton navclientes;
	TextView titulonavbar;	
	ImageButton opcionesbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datosventa);
		
		idventa = (TextView) findViewById(R.id.NoVentaLbl);
		fechaventa = (TextView) findViewById(R.id.FechaVentaLbl);
		montopagado = (TextView) findViewById(R.id.MontoPagadoLbl);
		
		noserie = (TextView) findViewById(R.id.NoSerieAutoLbl);
		marca =(TextView) findViewById(R.id.MarcaAutoLbl);
		modelo = (TextView) findViewById(R.id.ModeloAutoLbl);
		anio = (TextView) findViewById(R.id.AnioAutoLbl);
		color = (TextView) findViewById(R.id.ColorAutoLbl);
		precio = (TextView) findViewById(R.id.PrecioAutoLbl);
		
		nombretxt = (TextView) findViewById(R.id.NombreClienteLbl);
		edadtxt = (TextView) findViewById(R.id.EdadClienteLbl);
		domiciliotxt = (TextView) findViewById(R.id.DomicilioClienteLbl);
		localidadtxt = (TextView) findViewById(R.id.LocalidadClienteLbl);
		municipio = (TextView) findViewById(R.id.MunicipioClienteLbl);
		estado = (TextView) findViewById(R.id.EstadoClienteLbl);
		telefono = (TextView) findViewById(R.id.TelefonoClienteLbl);
		email = (TextView) findViewById(R.id.EmailClienteLbl);
		
		//Operacion sobre barras de navegacion
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		navventas = (ImageButton) findViewById(R.id.ventasnavbtn);
		navautos = (ImageButton) findViewById(R.id.autosnavbtn);
		navclientes = (ImageButton) findViewById(R.id.clientesnavbtn);
		navventas.setVisibility(View.GONE);
		opcionesbtn = (ImageButton) findViewById(R.id.opcionesnavbtn);
		//opcionesbtn.setVisibility(View.GONE);
		registerForContextMenu(opcionesbtn);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		titulonavbar.setText("Venta");
		
		//Recuperar los datos recibidos del activity que invoco
		Bundle extras = getIntent().getExtras();
		if(extras != null){
	        int noventa;
	        //noserieauto = extras.getString("NoSerie");
	        noventa = Integer.valueOf(extras.getString("Id"));
	        
			//Cuando se tenga conexion al webservice, se recuperaraá el objeto y se
			//mostrarán en la vista
            Venta venta =  new AccesoDatosVentas(this).DatosVenta(noventa);
             if(venta != null)
             {
                idventa.setText(String.valueOf(venta.getIdVenta()));
                fechaventa.setText(venta.getFecha());
                montopagado.setText("$ " + String.valueOf(venta.getMonto()));

                noserie.setText(venta.getAuto().getNoSerie());
                modelo.setText(venta.getAuto().getModelo());
                anio.setText(String.valueOf(venta.getAuto().getAnio()));
                color.setText(venta.getAuto().getColor());
                precio.setText("$ " + String.valueOf(venta.getAuto().getPrecio()));

                nombretxt.setText(venta.getCliente().getNombre() + " " + venta.getCliente().getApellidos());
                edadtxt.setText(String.valueOf(venta.getCliente().getEdad()));
                domiciliotxt.setText(venta.getCliente().getCalle() + " No. " + venta.getCliente().getNumero());
                estado.setText(venta.getCliente().getEstado());
                municipio.setText(venta.getCliente().getMunicipio());
                localidadtxt.setText(venta.getCliente().getLocalidad());
                telefono.setText(venta.getCliente().getTelefono());
                email.setText(venta.getCliente().getEmail());
             }else
             {
                 finish();
                 Toast.makeText(this, "Ocurrió un error al cargar los datos de la venta.", Toast.LENGTH_SHORT).show();
             }

		}
		
		
	       //---- Botones de barra de navegacion ----
			//Boton Clientes del Nav Bar
		navclientes.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View v) {
				    Intent ventanaclientes = new Intent(DatosVentaActivity.this, ClientesActivity.class);
				    startActivity(ventanaclientes);
				}
			});
			
        //Boton Autos
		navautos.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {			
			    Intent ventanaclientes = new Intent(DatosVentaActivity.this, AutosActivity.class);
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
		         
		         //mostrar men? contextual
		         //Boton opciones
		           opcionesbtn.setOnClickListener(new View.OnClickListener() {			
		   			public void onClick(View v) {				
		   				v.showContextMenu();
		   			}
		   		});
		              
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
		Intent ventanainicio = new Intent(DatosVentaActivity.this, Home.class);
	    startActivity(ventanainicio);
		return true;
	case R.id.CancelarOpt:		
		return true;
		}
		return super.onContextItemSelected(item);
		}	

}
