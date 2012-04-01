package mx.afelipelc.agencia2012.activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosAutos;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorAuto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorCliente;
import mx.afelipelc.agencia2012.models.Auto;

public class AutosActivity extends Activity {

	ListView listaautos;
	Button atrasbtn;
	ImageButton navautos;
	ImageButton navventas;
	TextView titulonavbar;
	ImageButton clientesbtn;
	ImageButton opcionesbtn;
    EditText buscadortxt;

    AccesoDatosAutos accesodatosautos;
	Auto[] autoslistaobjs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autos);
		
		listaautos = (ListView) findViewById(R.id.listaautos);
		atrasbtn = (Button) findViewById(R.id.AtrasBtn);
		buscadortxt = (EditText) findViewById(R.id.buscadorautos);
		//Ocultar el boton Clientes de la barra de navegación
		navautos = (ImageButton) findViewById(R.id.autosnavbtn);		
		navautos.setVisibility(View.GONE);
		
		navventas = (ImageButton) findViewById(R.id.ventasnavbtn);
		clientesbtn = (ImageButton) findViewById(R.id.clientesnavbtn);
		
		//Registrar el menu contextual
		opcionesbtn = (ImageButton) findViewById(R.id.opcionesnavbtn);
		//opcionesbtn.setOnCreateContextMenuListener(this);
		registerForContextMenu(opcionesbtn);
		
		//Mostrar un titulo en la barra de navegacion
		titulonavbar = (TextView) findViewById(R.id.TituloNavbar);
		titulonavbar.setText("Autos");
		
		//AdaptadorAuto adaptador = new AdaptadorAuto(this);
		//listaautos.setAdapter(adaptador);

        //Acceder a la lista de Autos llamando y procesando la respuesta del WS
        accesodatosautos = new AccesoDatosAutos(this);
        try
        {
            autoslistaobjs = accesodatosautos.ListaAutos();
            if(autoslistaobjs != null)
            {
                //AdaptadorAuto adaptador = new AdaptadorAuto(this,autoslistaobjs);
                listaautos.setAdapter(new AdaptadorAuto(this,autoslistaobjs));
            }else
                Toast.makeText(this, "La lista de autos está vacía, o no se pudo cargar la lista de autos (verifique que la URL de los WS sea correcta).", Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
            Log.d("Error en AutosActivity", e.getMessage() + " >>>" + e.getStackTrace());
            Toast.makeText(this, "Ocurrió un error al cargar la lista de autos.", Toast.LENGTH_SHORT).show();
            finish();
        }

		listaautos.setOnItemClickListener(new OnItemClickListener() {
	     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	       	//Al seleccionar
	     	  //Toast.makeText(parent.getContext(), "Seleccionado >>> " + position, position ).show();
	     	  //View item = listaautos.getChildAt(position);
	     	  	     	  
	     	 TextView idauto = (TextView) view.findViewById(R.id.AutoIdLbl);
	     	 //TextView noserie = (TextView) view.findViewById(R.id.NoSerieAutoLbl);
//	     	  Toast.makeText(parent.getContext(), "Seleccionado >>> " + nombrec.getText(), position).show();
//	     	  Toast.makeText(parent.getContext(), "El ID del cliente seleccionado es >>> " + idcliente.getText(), position).show();
	     	  
	     	  //Abrir el activity de Datos del auto
	     	  Intent ventanadatos = new Intent(AutosActivity.this, DatosAutoActivity.class);	 		    
	 		    ventanadatos.putExtra("Id", idauto.getText());
	 		    startActivity(ventanadatos);
	       	}
	     });

        //El textbox buscar
        buscadortxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() == 0)
                    listaautos.setAdapter(null);
                //si solo ha escrito 2 caracteres o mas de 8 caracteres, no hacer ninguna busqueda
                if(charSequence.length() < 3 || charSequence.length() > 10)
                    return;

                if(!buscadortxt.getText().toString().equals("")){
                    //Toast.makeText(getBaseContext(),"Busqueda vacia",Toast.LENGTH_SHORT).show();
                    listaautos.setAdapter(null);
                    autoslistaobjs = accesodatosautos.BuscarAutos(buscadortxt.getText().toString());

                    if(autoslistaobjs != null)
                    {
                        AdaptadorAuto adaptador = new AdaptadorAuto(getBaseContext(), autoslistaobjs);
                        listaautos.setAdapter(adaptador);
                    }

                }else
                    listaautos.setAdapter(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
	 		
		//Boton Clientes del Nav Bar
        clientesbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//AbrirOtra();
				
			    Intent ventanaclientes = new Intent(AutosActivity.this, ClientesActivity.class);
			    startActivity(ventanaclientes);
			}
		});
        
	      //Boton Ventas
        navventas.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
			    Intent ventanaventas = new Intent(AutosActivity.this, VentasActivity.class);
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
	         
	         //mostrar menú contextual
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
		
	}
	

	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	switch(item.getItemId()) {
	case R.id.AgregarOpt:
		//Ir al Activity AgregarAuto
	    Intent ventanaclientes = new Intent(AutosActivity.this, AgregarAutoActivity.class);
	    startActivity(ventanaclientes);
	return true;
	case R.id.InicioOpt:		
		Intent ventanainicio = new Intent(AutosActivity.this, Home.class);
	    startActivity(ventanainicio);
		return true;
	case R.id.CancelarOpt:		
		return true;
		}
		return super.onContextItemSelected(item);
		}	


}
