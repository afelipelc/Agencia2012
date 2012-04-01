package mx.afelipelc.agencia2012.activities;


import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import mx.afelipelc.agencia2012.data.Agencia2012DataSource;
import mx.afelipelc.agencia2012.data.RegistroConfig;


public class Home extends Activity {
    /** Called when the activity is first created. */
	
	//definir los botones
	//Deben importarse las clases correspondientes
	ImageButton clientesbtn;
	ImageButton autosbtn;
	ImageButton ventasbtn;

    Button irawebbtn;

    private Agencia2012DataSource datosdb;


    //elemento para el menu
    private final int MENU_CONF=1, MENU_SALIR=2;
    private final int GROUP_DEFAULT=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Establecer el layout a utilizar
        setContentView(R.layout.main);        

        //this.deleteDatabase("agencia2012.db");

        //Cargar los elementos de configuracion
        datosdb = new Agencia2012DataSource(this);
        datosdb.Open();

        RegistroConfig registroservices = datosdb.GetRegistro("urlservicios");
        if(registroservices!=null)
            //EDITTEXT urlservicios.setText(registroservices.getValor());
            //Almacenar como variable global la URL de los WS
            ((Agencia2012) getApplication()).setUrlServicios(registroservices.getValor());
        else
        {
            Toast.makeText(getApplicationContext(), "Error al cargar los datos de configuración.", Toast.LENGTH_LONG).show();
            finish();
        }
        datosdb.Close();

        //Comprobar que el usuario este autentificado, sino, pasarlo a login
        if(((Agencia2012) getApplication()).getUsuario() == null || ((Agencia2012) getApplication()).getUsuario().isAutentificado()==false )
        {
            Intent login = new Intent(Home.this, LoginActivity.class);
            startActivity(login);
            finish();
            return;
        }

        if(((Agencia2012) getApplication()).getUsuario().IsInRole("Administrador"))
            Toast.makeText(this,"El usuario "+ ((Agencia2012) getApplication()).getUsuario().getNombreUsuario() +" si tiene el rol de Administrador",Toast.LENGTH_SHORT).show();

        //inicializar los botones
        clientesbtn = (ImageButton) findViewById(R.id.clientesbtn);
        autosbtn = (ImageButton) findViewById(R.id.autosbtn);
        ventasbtn = (ImageButton) findViewById(R.id.ventasbtn);
        irawebbtn = (Button) findViewById(R.id.irawebbtn);
        //configbtn.setImageResource(android.R.drawable.ic_menu_manage);

        //Boton Clientes
        clientesbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//AbrirOtra();
				
			    Intent ventanaclientes = new Intent(Home.this, ClientesActivity.class);
			    startActivity(ventanaclientes);
			}
		});
        
      //Boton Autos
        autosbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent ventanaautos = new Intent(Home.this, AutosActivity.class);
			    startActivity(ventanaautos);
			}
		});
        
        //Boton Ventas
        ventasbtn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent ventanaventas = new Intent(Home.this, VentasActivity.class);
			    startActivity(ventanaventas);
			}
		});

        //Boton de configuracion
        irawebbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				 Intent browser = new Intent(Intent.ACTION_VIEW);
	                browser.setData(Uri.parse("http://afelipelc.mx"));
	                Toast.makeText(v.getContext(), "Abriendo el sitio >>> http://afelipelc.mx", Toast.LENGTH_SHORT).show();
	                startActivity(browser);
			}
		});
    }

    //el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(GROUP_DEFAULT, MENU_CONF, 0, "Configuración")
                .setIcon(R.drawable.config_img);
        menu.add(GROUP_DEFAULT, MENU_SALIR,0, "Cerrar sesión")
                .setIcon(R.drawable.logout);
        return super.onCreateOptionsMenu(menu);
    }

    //Al seleccionar una opcion del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case MENU_CONF:
                //Abrir el activity de configuracion
                Intent ventanaconfig = new Intent(Home.this, ConfiguracionActivity.class);
                startActivity(ventanaconfig);
                return true;
            case MENU_SALIR:
                //Abrir el activity de configuracion
                Intent ventanalogin = new Intent(Home.this, LoginActivity.class);
                startActivity(ventanalogin);
                //Eliminar el objeto usuario de la aplicacion
                ((Agencia2012) getApplication()).setUsuario(null);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}