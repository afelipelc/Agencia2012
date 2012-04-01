package mx.afelipelc.agencia2012.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosClientes;
import mx.afelipelc.agencia2012.models.Cliente;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DatosClienteActivity extends Activity {
	Button atrasbtn;
	
	Button municipio;
	Button estado;
	EditText telefono;
	EditText email;
	
	EditText nombretxt;
	EditText apellidostxt;
	EditText edadtxt;
	EditText domiciliotxt;
	EditText numerodomtxt;
	Button localidadtxt;
	
	//Barras de navegacion
	ImageButton navclientes;
	ImageButton navventas;
	TextView titulonavbar;
	TextView tituloeditbar;
	View editbar;
	View navbar;
	ImageButton opcionesbtn;
	Button cancelarbar;
	Button okbar;
	ImageButton autosbtn;
	
	String accionactual="vista", accionanterior="vista";

	Cliente cliente;
    int IdEstado, IdMunicipio, IdLocalidad;
    AccesoDatosClientes accesodatoscliente;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datoscliente);

		//Recuperar los elementos de la interfaz
		nombretxt = (EditText) findViewById(R.id.NombreClienteTxt);
		apellidostxt = (EditText) findViewById(R.id.ApellidosClienteTxt);
		edadtxt = (EditText) findViewById(R.id.EdadClienteTxt);
		domiciliotxt = (EditText) findViewById(R.id.CalleClienteTxt);
		numerodomtxt = (EditText) findViewById(R.id.NumeroDomClienteTxt);		
		localidadtxt = (Button) findViewById(R.id.LocalidadClienteTxt);
		municipio = (Button) findViewById(R.id.MpioClienteTxt);
		estado = (Button) findViewById(R.id.EstadoClienteTxt);
		telefono = (EditText) findViewById(R.id.TelefonoClienteTxt);
		email = (EditText) findViewById(R.id.EmailClienteTxt);

		//Operacion sobre barras de navegacion
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		autosbtn = (ImageButton) findViewById(R.id.autosnavbtn);
		
		navventas = (ImageButton) findViewById(R.id.ventasnavbtn);
		
		//Ocultar el boton Clientes de la barra de navegación
		navclientes = (ImageButton) findViewById(R.id.clientesnavbtn);		
		navclientes.setVisibility(View.GONE);
		
		//ocultar la barra de navegacion EditBar
		editbar = (View) findViewById(R.id.bareditinclude);
		editbar.setVisibility(View.GONE);
		navbar = (View) findViewById(R.id.navbarinclude);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		tituloeditbar= (TextView) findViewById(R.id.TituloEditBar);
		titulonavbar.setText("Datos del cliente");
		tituloeditbar.setText("Editar datos del cliente");
		
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
                //Registrar la accion anterior como actual para no
                //volver a habilitar el form
                //accionactual = accionanterior;
                //AlternarVista(accionanterior);
                //CargarDatos();
            }
        });
	
		//Recuperar los datos recibidos del activity que invoco
		if(getIntent().getExtras() != null)
		{
			Bundle extras = getIntent().getExtras();
	        int idcliente = extras.getInt("Id");

			
			//Cuando se tenga conexion al webservice, se recuperará el objeto y se
			//mostrarán en la vista
	        accesodatoscliente = new AccesoDatosClientes(this);
	        this.cliente = accesodatoscliente.DatosCliente(idcliente);
	               
		        if(this.cliente != null)
		        {
                    CargarDatos();
		        }else
				{
					finish();
					Toast.makeText(this, "Ocurrió un error al cargar los datos del cliente.", Toast.LENGTH_SHORT).show();
				}
		}
        //Maniupar la seleccion del lugar
		//Al presionar el boton de estado
        this.estado.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v)
	        {
	        	//Toast.makeText(contexto, "Presiono sobre estado", Toast.LENGTH_SHORT).show();
	        	
	        	Intent ventanalugares = new Intent(DatosClienteActivity.this, LugaresActivity.class);
	        	ventanalugares.putExtra("mostrar", "estados");
	        	startActivityForResult(ventanalugares, 0);	        	
	        }
        });

        //Al presionar el boton de municipio
        this.municipio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(cliente.getIdEstado() != 0)
                {
                    Intent ventanalugares = new Intent(DatosClienteActivity.this, LugaresActivity.class);
                    ventanalugares.putExtra("mostrar", "municipios");
                    ventanalugares.putExtra("idestado", IdEstado);
                    startActivityForResult(ventanalugares, 0);
                }else
                    Toast.makeText(getBaseContext(),"Seleccione el estado.", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar el boton de localidad
        this.localidadtxt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(cliente.getIdMunicipio() != 0)
                {
                Intent ventanalugares = new Intent(DatosClienteActivity.this, LugaresActivity.class);
                ventanalugares.putExtra("mostrar", "localidades");
                ventanalugares.putExtra("idmunicipio", IdMunicipio);
                startActivityForResult(ventanalugares, 0);
                }else
                    Toast.makeText(getBaseContext(),"Seleccione el municipio.", Toast.LENGTH_SHORT).show();
            }
        });
        
        //Fin manipulacion seleccion lugar        
        
		
        //---- Botones de barra de navegacion ----
		//Al presionar el boton atras
        this.atrasbtn.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v)
	        {
	        finish();
	        }
        });
        
        
        //Boton Autos
        autosbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//AbrirOtra();
				
			    Intent ventanaclientes = new Intent(DatosClienteActivity.this, AutosActivity.class);
			    startActivity(ventanaclientes);
			}
		});
        
	      //Boton Ventas
        navventas.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaventas = new Intent(DatosClienteActivity.this, VentasActivity.class);
			    startActivity(ventanaventas);
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
	        	
              //El codigo del boton ok ha sido movido al nuevo metodo GuardarDatos() que se encargue de guardar
               //ya que se implemento el cuadro de dialogo para confirmar
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
        //Actualizar los datos del objeto cliente
        cliente.setNombre(nombretxt.getText().toString());
        cliente.setApellidos(apellidostxt.getText().toString());
        cliente.setEdad(Integer.valueOf(edadtxt.getText().toString()));
        cliente.setCalle(domiciliotxt.getText().toString());
        cliente.setNumero(numerodomtxt.getText().toString());
        cliente.setTelefono(telefono.getText().toString());
        cliente.setEmail(email.getText().toString());
        cliente.setIdEstado(IdEstado);
        cliente.setEstado(estado.getText().toString());
        cliente.setIdMunicipio(IdMunicipio);
        cliente.setMunicipio(municipio.getText().toString());
        cliente.setIdLocalidad(IdLocalidad);
        cliente.setLocalidad(localidadtxt.getText().toString());
        //Guardar los cambios y alternar la vista
        if(accesodatoscliente.DatosCliente(cliente, "ActualizarCliente").isRealizado())
            Toast.makeText(getBaseContext(),"Se han guardado los datos del Cliente: " + cliente.getNombre()+ " "+ cliente.getApellidos(),Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getBaseContext(),"Ocurrió un error al intentar actualizar los datos del cliente: " + cliente.getNombre()+ " "+ cliente.getApellidos(),Toast.LENGTH_LONG).show();

        accionactual = accionanterior;
        AlternarVista(accionanterior);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);              
        if (requestCode == 0){
         if (resultCode == RESULT_OK) {
       	  //Toast.makeText(this, "Seleccionó el lugar: " +data.getStringExtra("idlugar") , Toast.LENGTH_SHORT).show();

             String mostrado =    data.getStringExtra("lugares");
             if(mostrado.equals("estados"))
             {
                 IdEstado = Integer.valueOf(data.getStringExtra("idlugar"));
                  //cliente.setIdEstado(Integer.valueOf(data.getStringExtra("idlugar")));
                  //cliente.setEstado(data.getStringExtra("nombrelugar"));
                  estado.setText(data.getStringExtra("nombrelugar"));

                  //quitar localidad y municipio
                  localidadtxt.setText("");
                  municipio.setText("");
                 //cliente.setIdMunicipio(0);
                 //cliente.setIdLocalidad(0);
                 IdMunicipio = 0;
                 IdLocalidad = 0;
             }

             if(mostrado.equals("municipios"))
             {
                 IdMunicipio = Integer.valueOf(data.getStringExtra("idlugar"));
                 //cliente.setIdMunicipio(Integer.valueOf(data.getStringExtra("idlugar")));
                 //cliente.setMunicipio(data.getStringExtra("nombrelugar"));
                 municipio.setText(data.getStringExtra("nombrelugar"));

                 //quitar localidad
                 localidadtxt.setText("");
                 //cliente.setIdLocalidad(0);
                 IdLocalidad = 0;
             }

             if(mostrado.equals("localidades"))
             {
                 IdLocalidad = Integer.valueOf(data.getStringExtra("idlugar"));
                 //cliente.setIdLocalidad(Integer.valueOf(data.getStringExtra("idlugar")));
                 //cliente.setLocalidad(data.getStringExtra("nombrelugar"));
                 localidadtxt.setText(data.getStringExtra("nombrelugar"));
             }

         }
         else{
          Toast.makeText(this, "No se seleccionó ningún lugar.", Toast.LENGTH_SHORT).show();
         }
        }
	}

	//el menu contextual
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Opciones");
		menu.setHeaderIcon(R.drawable.options_small);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.opcionesmenu, menu);
		
	}
		
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
//		Toast.makeText(super.getBaseContext(), "opcion seleccionada.", Toast.LENGTH_SHORT).show();
//		return true;
	switch(item.getItemId()) {
	case R.id.AgregarOpt:
		//Ir al Activity AgregarCliente
	    Intent ventanaclientes = new Intent(DatosClienteActivity.this, AgregarClienteActivity.class);
	    startActivity(ventanaclientes);
	return true;
	case R.id.EditarOpt:
		Toast.makeText(super.getBaseContext(), "Seleccionado >>> editar ", Toast.LENGTH_SHORT).show();
		//Alternar Vista
		this.AlternarVista("editar");
		
		
		return true;
	case R.id.InicioOpt:
		//Toast.makeText(super.getBaseContext(), "Seleccionado >>> inicio ", Toast.LENGTH_SHORT).show();
		Intent ventanainicio = new Intent(DatosClienteActivity.this, Home.class);
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
		nombretxt.setEnabled(habilitar);
        apellidostxt.setEnabled(habilitar);
		edadtxt.setEnabled(habilitar);
		domiciliotxt.setEnabled(habilitar);
		estado.setEnabled(habilitar);
        municipio.setEnabled(habilitar);
        localidadtxt.setEnabled(habilitar);
		numerodomtxt.setEnabled(habilitar);
		telefono.setEnabled(habilitar);
		email.setEnabled(habilitar);
	}

    private  void CargarDatos()
    {
        nombretxt.setText(cliente.getNombre());
        apellidostxt.setText(cliente.getApellidos());
        //Log.d("Edad Cliente:", String.valueOf(cliente.getEdad()));
        edadtxt.setText(String.valueOf(cliente.getEdad()));
        domiciliotxt.setText(cliente.getCalle());
        numerodomtxt.setText(cliente.getNumero());
        estado.setText(cliente.getEstado());
        municipio.setText(cliente.getMunicipio());
        localidadtxt.setText(cliente.getLocalidad());
        telefono.setText(cliente.getTelefono());
        email.setText(cliente.getEmail());
        IdEstado = cliente.getIdEstado();
        IdMunicipio = cliente.getIdMunicipio();
        IdLocalidad = cliente.getIdLocalidad();
    }
}
