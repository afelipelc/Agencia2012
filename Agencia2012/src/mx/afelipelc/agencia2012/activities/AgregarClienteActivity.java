package mx.afelipelc.agencia2012.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosClientes;
import mx.afelipelc.agencia2012.models.Cliente;
import mx.afelipelc.agencia2012.models.ResultadoService;

public class AgregarClienteActivity extends Activity {

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
	TextView tituloeditbar;
	View editbar;
	View navbar;
	Button cancelarbar;
	Button okbar;

    Context contexto;

    Cliente cliente = new Cliente();
    AccesoDatosClientes accesodatoscliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datoscliente);

        contexto = this.getBaseContext();

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

		//ocultar la barra de navegacion y mostrar la de Edit
		editbar = (View) findViewById(R.id.bareditinclude);
		editbar.setVisibility(View.VISIBLE);
		navbar = (View) findViewById(R.id.navbarinclude);
		navbar.setVisibility(View.GONE);
		
		//Mostrar un titulo en la barra de navegacion
		tituloeditbar= (TextView) findViewById(R.id.TituloEditBar);
		tituloeditbar.setText("Nuevo Cliente");
		
		cancelarbar = (Button) findViewById(R.id.CancelOptBtn);
		okbar = (Button) findViewById(R.id.OkBtn);
		//Fin de operaciones sobre barra de navegacion

        accesodatoscliente = new AccesoDatosClientes(this);

		this.HabilitarTextBoxes(true);

        //Dialogo Yes NO
        final AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setMessage("¿Confirma registrar el nuevo cliente?");
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
            //no hacer nada
            }
        });

        //Maniupar la seleccion del lugar
        //Al presionar el boton de estado
        this.estado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                //Toast.makeText(contexto, "Presiono sobre estado", Toast.LENGTH_SHORT).show();

                Intent ventanalugares = new Intent(AgregarClienteActivity.this, LugaresActivity.class);
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
                    Intent ventanalugares = new Intent(AgregarClienteActivity.this, LugaresActivity.class);
                    ventanalugares.putExtra("mostrar", "municipios");
                    ventanalugares.putExtra("idestado", cliente.getIdEstado());
                    startActivityForResult(ventanalugares, 0);
                }else
                    Toast.makeText(contexto,"Seleccione el estado.", Toast.LENGTH_SHORT).show();
            }
        });

        //Al presionar el boton de localidad
        this.localidadtxt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(cliente.getIdMunicipio() != 0)
                {
                    Intent ventanalugares = new Intent(AgregarClienteActivity.this, LugaresActivity.class);
                    ventanalugares.putExtra("mostrar", "localidades");
                    ventanalugares.putExtra("idmunicipio", cliente.getIdMunicipio());
                    startActivityForResult(ventanalugares, 0);
                }else
                    Toast.makeText(contexto,"Seleccione el municipio.", Toast.LENGTH_SHORT).show();
            }
        });

        //Fin manipulacion seleccion lugar

	       this.cancelarbar.setOnClickListener(new View.OnClickListener(){
		        public void onClick(View v)
		        {
		        	finish();
		        }
	        });
	        
	        
	        this.okbar.setOnClickListener(new View.OnClickListener(){
		        public void onClick(View v)
		        {
                     confirmar.show();
		        }
	        });
	        
	        
	}

    private void GuardarDatos()
    {
        cliente.setId(0);
        cliente.setNombre(nombretxt.getText().toString());
        cliente.setApellidos(apellidostxt.getText().toString());
        cliente.setEdad(Integer.valueOf(edadtxt.getText().toString()));
        //cliente.setIdLocalidad(##);
        cliente.setCalle(domiciliotxt.getText().toString());
        cliente.setNumero(numerodomtxt.getText().toString());
        cliente.setTelefono(telefono.getText().toString());
        cliente.setEmail(email.getText().toString());

        //Guardar los datos y redireccionarlo a la vista de datos
        ResultadoService resultado = accesodatoscliente.DatosCliente(cliente, "RegistrarCliente");
        if(resultado.isRealizado())
        {
            Toast.makeText(contexto, "Se ha registrado el Cliente: " + cliente.getNombre() + " " + cliente.getApellidos(), Toast.LENGTH_SHORT).show();
            Intent iradatos = new Intent(AgregarClienteActivity.this, DatosClienteActivity.class);
            iradatos.putExtra("Id", resultado.getIdReg());
            startActivity(iradatos);
            finish();
        }
        else
            Toast.makeText(contexto,"Ocurrió un error al intentar registrar el cliente: " + cliente.getNombre()+ " "+ cliente.getApellidos(),Toast.LENGTH_SHORT).show();
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
                    cliente.setIdEstado(Integer.valueOf(data.getStringExtra("idlugar")));
                    cliente.setEstado(data.getStringExtra("nombrelugar"));
                    estado.setText(data.getStringExtra("nombrelugar"));

                    //quitar localidad y municipio
                    localidadtxt.setText("");
                    municipio.setText("");
                    cliente.setIdMunicipio(0);
                    cliente.setIdLocalidad(0);
                }

                if(mostrado.equals("municipios"))
                {
                    cliente.setIdMunicipio(Integer.valueOf(data.getStringExtra("idlugar")));
                    cliente.setMunicipio(data.getStringExtra("nombrelugar"));
                    municipio.setText(data.getStringExtra("nombrelugar"));

                    //quitar localidad
                    localidadtxt.setText("");
                    cliente.setIdLocalidad(0);
                }

                if(mostrado.equals("localidades"))
                {
                    cliente.setIdLocalidad(Integer.valueOf(data.getStringExtra("idlugar")));
                    cliente.setLocalidad(data.getStringExtra("nombrelugar"));
                    localidadtxt.setText(data.getStringExtra("nombrelugar"));
                }

            }
            else{
                Toast.makeText(this, "No se seleccionó ningún lugar.", Toast.LENGTH_SHORT).show();
            }
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

}
