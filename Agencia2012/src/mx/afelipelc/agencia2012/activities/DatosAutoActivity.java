package mx.afelipelc.agencia2012.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.*;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosAutos;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorSpinner;
import mx.afelipelc.agencia2012.models.Auto;
import mx.afelipelc.agencia2012.models.MarcaAuto;

public class DatosAutoActivity extends Activity {

	Button atrasbtn;
	
	EditText noserie;
	Spinner marca;
	EditText modelo;
	EditText anio;
	EditText color;
	EditText precio;
	TextView vendido;
	
	//Barras de navegacion
	ImageButton navautos;
	ImageButton navventas;
	TextView titulonavbar;
	TextView tituloeditbar;
	View editbar;
	View navbar;
	ImageButton opcionesbtn;
	Button cancelarbar;
	Button okbar;
	ImageButton clientesbtn;
	
	Button venderauto;
    Button verventa;
	
	String accionactual="vista", accionanterior="vista";
	Auto auto;
    int IdMarca;
    String NombreMarca;
    MarcaAuto[] marcas;
    AccesoDatosAutos accesodatosautos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datosauto);
		
		//Recuperar todos los elementos
		noserie = (EditText) findViewById(R.id.NoSerieAutoTxt);
		marca =(Spinner) findViewById(R.id.MarcaAutoCmb);
		modelo = (EditText) findViewById(R.id.ModeloAutoTxt);
		anio = (EditText) findViewById(R.id.AnioAutoTxt);
		color = (EditText) findViewById(R.id.ColorAutoTxt);
		precio = (EditText) findViewById(R.id.PrecioAutoTxt);
		vendido =(TextView) findViewById(R.id.VendidoAutoLbl);
		
		venderauto = (Button) findViewById(R.id.VenderAutoBtn);
		verventa = (Button) findViewById(R.id.VerVentaBtn);

		//Operacion sobre barras de navegacion
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		navventas = (ImageButton) findViewById(R.id.ventasnavbtn);
		//Ocultar el boton Autos de la barra de navegación
		navautos = (ImageButton) findViewById(R.id.autosnavbtn);		
		navautos.setVisibility(View.GONE);
		
		clientesbtn = (ImageButton) findViewById(R.id.clientesnavbtn);
		
		//ocultar la barra de navegacion EditBar
		editbar = (View) findViewById(R.id.bareditinclude);
		editbar.setVisibility(View.GONE);
		navbar = (View) findViewById(R.id.navbarinclude);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		tituloeditbar= (TextView) findViewById(R.id.TituloEditBar);
		titulonavbar.setText("Datos del auto");
		tituloeditbar.setText("Editar datos del auto");
		
		cancelarbar = (Button) findViewById(R.id.CancelOptBtn);
		okbar = (Button) findViewById(R.id.OkBtn);
		//Fin de operaciones sobre barra de navegacion
		
		//Registrar el menu contextual
		opcionesbtn = (ImageButton) findViewById(R.id.opcionesnavbtn);
		//opcionesbtn.setOnCreateContextMenuListener(this);
		registerForContextMenu(opcionesbtn);

        //Dialogo Yes NO
        final AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setMessage("¿Confirma guardar los cambios realizados?");
        //Boton Si en cuadro de dialogo
        confirmar.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Si dijo que si, entonces guardar los datos
                GuardarDatos();
            }
        });

        confirmar.setNegativeButton("Calcelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        //Recuperar los datos recibidos del activity que invoco
        if(getIntent().getExtras() != null)
        {
            Bundle extras = getIntent().getExtras();
            //Log.d("Id recibido", "El Id del auto en Extras: " + extras.getString("Id"));
            int idauto = Integer.valueOf(extras.getString("Id"));
            //Log.d("Id recibido", "El Id del auto parseado es: " + idauto);
            //Cuando se tenga conexion al webservice, se recuperará el objeto y se
            //mostrarán en la vista
            accesodatosautos = new AccesoDatosAutos(this);
            this.auto = accesodatosautos.DatosAuto(idauto);
            if(auto != null)
            {
                //Inflar el Spinner de marcas

                marcas = accesodatosautos.MarcasAuto();
                if(marcas != null)
                {
                    marca.setPrompt("Marca");
                    marca.setAdapter(new AdaptadorSpinner(this,0,marcas));
                }

                CargarDatos();

            }else
            {
                finish();
                Toast.makeText(this, "Ocurrió un error al cargar los datos del auto.", Toast.LENGTH_LONG).show();
            }
        }
        Log.d("Id de venta del auto:",">>> " + auto.getIdVenta());
         //Al seleccionar elemento del spinner
        marca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                TextView IdMarcaSelect = (TextView) view.findViewById(R.id.IdLbl);
                TextView NombreMarc = (TextView) findViewById(R.id.NombreLbl);
                //Log.d("Marca seleccionada","Id Marca seleccionada: " + IdMarcaSelect.getText());
                IdMarca = Integer.valueOf(IdMarcaSelect.getText().toString());
                NombreMarca = NombreMarc.getText().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                return;
            }
        });
        
        //---- Botones de barra de navegacion ----
		//Boton Clientes del Nav Bar
        clientesbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//AbrirOtra();
				
			    Intent ventanaclientes = new Intent(DatosAutoActivity.this, ClientesActivity.class);
			    startActivity(ventanaclientes);
			}
		});
		
	      //Boton Ventas
        navventas.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaventas = new Intent(DatosAutoActivity.this, VentasActivity.class);
			    startActivity(ventanaventas);
			}
		});
        
	      //Boton VenderAuto
        venderauto.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventaauto = new Intent(DatosAutoActivity.this, VenderAutoActivity.class);
			    ventaauto.putExtra("Id", String.valueOf(auto.getId()));
			    startActivity(ventaauto);
			}
		});

        verventa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent verventa = new Intent(DatosAutoActivity.this, DatosVentaActivity.class);
                verventa.putExtra("Id", String.valueOf(auto.getIdVenta()));
                startActivity(verventa);
            }
        });

	 		//Al presionar el boton atras
	         this.atrasbtn.setOnClickListener(new View.OnClickListener(){
	 	        public void onClick(View v)
	 	        {
	 	        finish();
	 	        }
	         });
	         
	         this.cancelarbar.setOnClickListener(new View.OnClickListener(){
	 	        public void onClick(View v)
	 	        {
	 	        	//Registrar la accion anterior como actual para no
	 	        	//volver a habilitar el form
	 	        	//accionactual = accionanterior;
	 	        	AlternarVista(accionanterior);
                    CargarDatos();
	 	        }
	         });

	         this.okbar.setOnClickListener(new View.OnClickListener(){
	 	        public void onClick(View v)
	 	        {
                   confirmar.show();
	 	        }
	         });
	         
	         //mostrar menú contextual
	         //Boton opciones
	           opcionesbtn.setOnClickListener(new View.OnClickListener() {			
	   			public void onClick(View v) {				
	   				v.showContextMenu();
	   			}
	   		});
	       //---- Botones de barra de navegacion ----
	}

    private void GuardarDatos()
    {
        //Guardar los cambios y alternar la vista
        auto.setNoSerie(noserie.getText().toString());
        auto.getMarca().setIdMarca(IdMarca);
        auto.getMarca().setNombre(NombreMarca);
        auto.setModelo(modelo.getText().toString());
        auto.setAnio(Integer.valueOf(anio.getText().toString()));
        auto.setColor(color.getText().toString());
        auto.setPrecio(Float.valueOf(precio.getText().toString()));
        //Llamar al WS que guardara los datos
        if(accesodatosautos.DatosAuto(auto, "ActualizarAuto").isRealizado())
            Toast.makeText(getBaseContext(),"Se han guardado los datos del Auto: " + auto.getNoSerie(),Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getBaseContext(),"Ocurrió un error al intentar actualizar los datos del auto: " + auto.getNoSerie(),Toast.LENGTH_LONG).show();

        accionactual = accionanterior;
        AlternarVista(accionanterior);
    }

	//el menu contextual
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Opciones");
		menu.setHeaderIcon(R.drawable.options_small);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.opcionesmenu, menu);
	   
		MenuItem opcionnuevo;
		opcionnuevo = menu.findItem(R.id.AgregarOpt);
		opcionnuevo.setIcon(R.drawable.add_client);
				
//		menu.setHeaderTitle("Opciones");
//		menu.add("opcion 1");
		
	}
		
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
	switch(item.getItemId()) {
	case R.id.AgregarOpt:
		//Ir al Activity AgregarAuto
	    Intent ventanaautos = new Intent(DatosAutoActivity.this, AgregarAutoActivity.class);
	    startActivity(ventanaautos);
	    return true;
	case R.id.EditarOpt:
        //Alternar Vista
        if(auto.isVendido())
            Toast.makeText(getBaseContext(),"Este auto ya se ha vendido y no pueden editarse sus datos.",Toast.LENGTH_SHORT).show();
        else
            this.AlternarVista("editar");

		return true;
	case R.id.InicioOpt:
		Intent ventanainicio = new Intent(DatosAutoActivity.this, Home.class);
	    startActivity(ventanainicio);
		return true;
	case R.id.CancelarOpt:		
		return true;
		}
		return super.onContextItemSelected(item);
		}	

	private void AlternarVista(String Accion)
	{
		//guardar la accion actual como historial
		this.accionanterior = this.accionactual;
		this.accionactual = Accion;
		//cambiar barras de navegacion
		if(this.accionactual == "editar")
		{
			//ocultar la barra de navegacion navbar y mostrar EditBar
			navbar.setVisibility(View.GONE);
			editbar.setVisibility(View.VISIBLE);
			
			//habilitar textboxes
			this.HabilitarTextBoxes(true);
		}
		
		if(this.accionactual=="vista")
		{
			//mostrar la barra de navegacion navbar y ocultar EditBar
			navbar.setVisibility(View.VISIBLE);
			editbar.setVisibility(View.GONE);
			
			//deshabilitar textboxes
			this.HabilitarTextBoxes(false);
		}
	}

	private void HabilitarTextBoxes(Boolean habilitar)
	{		
		noserie.setEnabled(habilitar);
		modelo.setEnabled(habilitar);
		anio.setEnabled(habilitar);
				
		color.setEnabled(habilitar);
		precio.setEnabled(habilitar);
		marca.setClickable(habilitar);
	}

    private void CargarDatos()
    {
        if(auto != null)
        {
            noserie.setText(auto.getNoSerie());
            modelo.setText(auto.getModelo());
            anio.setText(String.valueOf(auto.getAnio()));
            color.setText(auto.getColor());
            precio.setText(String.valueOf(auto.getPrecio()));
            if(auto.isVendido())
            {
                vendido.setText("Si");
                venderauto.setVisibility(View.GONE);

            }
            else
            {
                vendido.setText("No");
                verventa.setVisibility(View.GONE);
            }

                int idcontador=0;
                for(MarcaAuto marcaitem : marcas)
                {
                    if(marcaitem.getNombre().equals(auto.getMarca().getNombre()))
                    {
                        //Log.d("Marca", "Marca: " + marca.getNombre() + " ID: " + marca.getIdMarca() + " -- " +idcontador);
                        marca.setSelection(idcontador);
                    }
                    idcontador++;
                }
        }
    }
}