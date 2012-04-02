package mx.afelipelc.agencia2012.activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import mx.afelipelc.agencia2012.models.ElementoSimple;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosClientes;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorCliente;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class ClientesActivity extends Activity {

	ListView listaclientes;
	Button atrasbtn;
	ImageButton navclientes;	
	ImageButton navventas;
	TextView titulonavbar;
	ImageButton autosbtn;
	ImageButton opcionesbtn;
    EditText buscadortxt;
    AccesoDatosClientes accesodatoscliente;
   	//String[] listaelementos = {"Elemento 1", "Elemento 2", "Elemento 3","Elemento 4","Elemento 5"};

    ElementoSimple[] listasimpleclientes;

//Las actividades que cargan listas deben mejorarse, ya que al tener datos cargados y rotar el dispositivo, estos datos
//ya no seran visibles, por lo tanto, se requiere la implementacion del evento onRestart() que redimensione la actividad
//con nuevos cambios
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientes);
		
        // actividad = this;
		listaclientes = (ListView) findViewById(R.id.listaclientes);
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		autosbtn = (ImageButton) findViewById(R.id.autosnavbtn);
		//Ocultar el boton Clientes de la barra de navegaci�n
		navclientes = (ImageButton) findViewById(R.id.clientesnavbtn);		
		navclientes.setVisibility(View.GONE);
		
		navventas = (ImageButton) findViewById(R.id.ventasnavbtn);
        
        buscadortxt = (EditText) findViewById(R.id.buscadorclientes);
        
		//Registrar el menu contextual
		opcionesbtn = (ImageButton) findViewById(R.id.opcionesnavbtn);
		//opcionesbtn.setOnCreateContextMenuListener(this);
		registerForContextMenu(opcionesbtn);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		titulonavbar.setText("Clientes");
		
		//listaclientes.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaelementos));
//		listaclientes.setAdapter(new ArrayAdapter(this,R.layout.list_item,clientes));
//        
//		listaclientes.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            	//Al seleccionar
//            }
//          });

//		//Llamar el adaptador de clientes
//		//AdaptadorCliente adaptador = new AdaptadorCliente(this);
//		//listaclientes.setAdapter(adaptador);
		
		
//		//Llamar al web service que provee los datos 
//		//y Llamar al adaptador de clientes al obtener el resultado del WS
//	
//		//Constantes de acceso al web service
//		//Estructurar la ACCION que es: espacio_nombres_ws/Nombre_del_metodo_a_invocar
//		String soap_accion = "agencia2012.afelipelc.mx/ListaClientes";
//		//Nombre del M�todo a invocar en el WS
//		String Metodo_Servicio = "ListaClientes";
//		//Espacio de nombres del WS a invocar
//		String Espacio_Nombres_WS = "agencia2012.afelipelc.mx";
//		//URL del Servidor en la que se ubica el Web Service a invocar
//		String URL_Servicios_Web ="http://172.16.239.237:8080/webservices/clientes.asmx";
//		//Variables necesarias para invocar al WS con kSoap2-Android
//		SoapObject request;
//		SoapSerializationEnvelope envelope;
//		SoapPrimitive  resultsRequestSOAP=null;
//		
//		//Inicializar el objeto que invocar� al m�todo del WS
//		request = new SoapObject(Espacio_Nombres_WS, Metodo_Servicio);
//		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//		//Habilitar compatibilidad de codificaci�n con los WS .NET
//		envelope.dotNet = true;
//		
//		envelope.setOutputSoapObject(request);
//		
//		//Crear el objeto que establecera la conexion al WS
//		HttpTransportSE transporte = new HttpTransportSE(URL_Servicios_Web);
//		
//		//Variable donde se almacenara el resultado (respuesta) del WS
//		
//		String  resultado = "";
//		//iniciar la llamada al web service controlando las excepciones
//		try {
//			
//			//invocar al WS
//			transporte.call(soap_accion, envelope);
//			//Obtener la respuesta del WS
//			resultsRequestSOAP = (SoapPrimitive)envelope.getResponse();
//			//Convertir a String la respuesta del WS
//			resultado = resultsRequestSOAP.toString();
//		
//		} catch (Exception e) {
//			// informar del error
//			e.printStackTrace();					
//		}
//		
//		//Todo lo que se pretenda hacer con el Resultado
//		//como convertirlo a objetos Java y mostrarlos en las interfaces
		
		//Acceder a la lista de clientes llamando y procesando la respuesta del WS
		accesodatoscliente = new AccesoDatosClientes(this);
		
		try{
            listasimpleclientes = accesodatoscliente.ListaClientes();
            if(listasimpleclientes != null)
            {
                //AdaptadorCliente adaptador = new AdaptadorCliente(this, listasimpleclientes);
	            listaclientes.setAdapter(new AdaptadorCliente(this, listasimpleclientes));
            }else
                Toast.makeText(this, "La lista de clientes est� vac�a, o no se pudo cargar la lista de clientes (verifique que la URL de los WS sea correcta).", Toast.LENGTH_SHORT).show();

		}catch(Exception e)
		{
            Log.d("Error en ClientesActivity", e.getMessage() + " >>>" + e.getStackTrace());
			Toast.makeText(this, "Ocurri� un error al cargar la lista de clientes.", Toast.LENGTH_SHORT).show();
            finish();
		}
		

        //El textbox buscar
        buscadortxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() == 0)
                    listaclientes.setAdapter(null);

                //si solo ha escrito 2 caracteres o mas de 8 caracteres, no hacer ninguna busqueda
                if(charSequence.length() < 3 || charSequence.length() > 10)
                    return;

//                if(buscadortxt.getText().toString().equals(""))
//                    Toast.makeText(getBaseContext(),"Busqueda: vacia",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getBaseContext(),"Busqueda: -" + buscadortxt.getText()+"-",Toast.LENGTH_SHORT).show();

                if(!buscadortxt.getText().toString().equals("")){
                    //Toast.makeText(getBaseContext(),"Busqueda vacia",Toast.LENGTH_SHORT).show();
                    listaclientes.setAdapter(null);
                    listasimpleclientes = accesodatoscliente.BuscarClientes(buscadortxt.getText().toString());

                    if(listasimpleclientes != null)
                    {
                        //AdaptadorCliente adaptador = new AdaptadorCliente(getBaseContext(), listasimpleclientes);
                        listaclientes.setAdapter(new AdaptadorCliente(getBaseContext(), listasimpleclientes));
                    }

                }else
                    listaclientes.setAdapter(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

		listaclientes.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      	//Al seleccionar
    	  //Toast.makeText(parent.getContext(), "Seleccionado >>> " + position, position ).show();
    	  //View item = listaclientes.getChildAt(position);
    	      	 
    	  TextView idcliente = (TextView)view.findViewById(R.id.IdLbl);
//    	  Toast.makeText(parent.getContext(), "Seleccionado >>> " + nombrec.getText(), position).show();
//    	  Toast.makeText(parent.getContext(), "El ID del cliente seleccionado es >>> " + idcliente.getText(), position).show();
    	  
    	  //ClienteListItem clienteob= new ClienteListItem(Integer.getInteger(idcliente.getText().toString()), nombrec.getText().toString());
    	  Bundle datosextras = new Bundle();
    	  //datosextras.pu("clienteobj", clienteob);
    	  datosextras.putInt("Id", Integer.valueOf(idcliente.getText().toString()));
    	  
    	  
    	  //Abrir el activity de Datos del cliente
    	  Intent ventanadatos = new Intent(ClientesActivity.this, DatosClienteActivity.class);
    	  
    	  ventanadatos.putExtras(datosextras);
    	  //ventanadatos.putExtra("Nombre", nombrec.getText());
		   // ventanadatos.putExtra("Id", idcliente.getText());
		    
		    startActivity(ventanadatos);
      }
    });
		
	      //Boton Autos
        autosbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//AbrirOtra();
				
			    Intent ventanaclientes = new Intent(ClientesActivity.this, AutosActivity.class);
			    startActivity(ventanaclientes);
			}
		});

	      //Boton Ventas
        navventas.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaventas = new Intent(ClientesActivity.this, VentasActivity.class);
			    startActivity(ventanaventas);
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
	   
		MenuItem opcionnuevo;
		opcionnuevo = menu.findItem(R.id.AgregarOpt);
		opcionnuevo.setIcon(R.drawable.add_client);
				
//		menu.setHeaderTitle("Opciones");
//		menu.add("opcion 1");
		
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
//		Toast.makeText(super.getBaseContext(), "opcion seleccionada.", Toast.LENGTH_SHORT).show();
//		return true;
	switch(item.getItemId()) {
	case R.id.AgregarOpt:
		//Ir al Activity AgregarCliente
	    Intent ventanaclientes = new Intent(ClientesActivity.this, AgregarClienteActivity.class);
	    startActivity(ventanaclientes);
	return true;
//	case R.id.EditarOpt:
//		Toast.makeText(super.getBaseContext(), "Seleccionado >>> editar ", Toast.LENGTH_SHORT).show();
//		return true;
	case R.id.InicioOpt:
		//Toast.makeText(super.getBaseContext(), "Seleccionado >>> inicio ", Toast.LENGTH_SHORT).show();
		Intent ventanainicio = new Intent(ClientesActivity.this, Home.class);
	    startActivity(ventanainicio);
		return true;
	case R.id.CancelarOpt:		
		return true;
		}
		return super.onContextItemSelected(item);
		}	

	
    

    
}
