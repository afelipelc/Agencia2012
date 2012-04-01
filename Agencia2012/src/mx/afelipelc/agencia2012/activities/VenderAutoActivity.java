package mx.afelipelc.agencia2012.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.*;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosAutos;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosClientes;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosVentas;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorAutoComplete;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorCliente;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorSpinner;
import mx.afelipelc.agencia2012.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VenderAutoActivity extends Activity {

	Context contexto = super.getBaseContext();
	TextView noserie;
	TextView marca;
	TextView modelo;
	TextView anio;
	TextView color;
	TextView precio;
	
	TextView municipio;
	TextView estado;
	
	AutoCompleteTextView nombretxt;
	TextView domiciliotxt;	
	TextView localidadtxt;
	
	TextView fechaventa;
	EditText montopagado;
	
	Button cancelarbar;
	Button okbar;

    Auto auto;
    Cliente cliente;
    ElementoSimple[] listaclientes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevaventa);
		
		noserie = (TextView) findViewById(R.id.NoSerieAutoLbl);
		marca =(TextView) findViewById(R.id.MarcaAutoLbl);
		modelo = (TextView) findViewById(R.id.ModeloAutoLbl);
		anio = (TextView) findViewById(R.id.AnioAutoLbl);
		color = (TextView) findViewById(R.id.ColorAutoLbl);
		precio = (TextView) findViewById(R.id.PrecioAutoLbl);
		
		nombretxt = (AutoCompleteTextView) findViewById(R.id.NombreClienteTxtAComplete);		
		domiciliotxt = (TextView) findViewById(R.id.DomicilioClienteLbl);
		localidadtxt = (TextView) findViewById(R.id.LocalidadClienteLbl);
		municipio = (TextView) findViewById(R.id.MunicipioClienteLbl);
		estado = (TextView) findViewById(R.id.EstadoClienteLbl);
		
		fechaventa = (TextView) findViewById(R.id.FechaVentaLbl);
		montopagado = (EditText) findViewById(R.id.MontoPagadoTxt);
		
		cancelarbar = (Button) findViewById(R.id.CancelOptBtn);
		okbar = (Button) findViewById(R.id.OkBtn);

        //Dialogo Yes NO
        final AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setMessage("¿Confirma registrar la venta de este auto?");
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
		
		//Recuperar los datos recibidos del activity que invoco
        if(getIntent().getExtras() != null)
        {
            Bundle extras = getIntent().getExtras();
            int idauto = Integer.valueOf(extras.getString("Id"));

            auto = new AccesoDatosAutos(this).DatosAuto(idauto);
            if(auto !=null)
            {
                if(auto.isVendido())
                {
                    Toast.makeText(this, "El auto: " + auto.getNoSerie() + " ya ha sido vendido.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                noserie.setText(auto.getNoSerie());
                marca.setText(auto.getMarca().getNombre());
                modelo.setText(auto.getModelo());
                anio.setText(String.valueOf(auto.getAnio()));
                color.setText(auto.getColor());
                precio.setText("$ "+ String.valueOf(auto.getPrecio()));

            }else
            {
                finish();
                Toast.makeText(this, "Ocurrió un error al cargar los datos del auto a vender.", Toast.LENGTH_SHORT).show();
            }
        
        }

        //Text Autocomplementar

        nombretxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (((AutoCompleteTextView) nombretxt).isPerformingCompletion()) {
                    return;
                }

                //si solo ha escrito 2 caracteres o mas de 8 caracteres, no hacer ninguna busqueda
                if(charSequence.length() < 3 || charSequence.length() > 8)
                    return;

                if (!nombretxt.getText().equals("")) {
                    listaclientes = new AccesoDatosClientes(getBaseContext()).BuscarClientes(nombretxt.getText().toString());
                    if (listaclientes != null) {
                            List<HashMap<String, String>> mapclientes = new ArrayList<HashMap<String, String>>();
                            for (ElementoSimple item : listaclientes) {
                                HashMap<String, String> mapcliente = new HashMap<String, String>();
                                mapcliente.put("Id", String.valueOf(item.getId()));
                                mapcliente.put("Nombre", item.getNombre());
                                mapclientes.add(mapcliente);
                            }
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), mapclientes, R.layout.spinner_item, new String[]{"Id", "Nombre"}, new int[]{R.id.IdLbl, R.id.NombreLbl});
                            nombretxt.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nombretxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Al seleccionar un cliente de la lista, obtener el ID
                //Recuperar sus datos del WS y mostrarlos en los TextView

                TextView idcliente = (TextView)view.findViewById(R.id.IdLbl);

                //cargar los datos del cliente
                cliente = new AccesoDatosClientes(getBaseContext()).DatosCliente(Integer.valueOf(idcliente.getText().toString()));

                //Si se encontro el cliente, mostrar todos sus datos
                if(cliente != null)
                {
                    nombretxt.setText(cliente.getNombre() + " " + cliente.getApellidos());
                    domiciliotxt.setText(cliente.getCalle() + " No. " + cliente.getNumero());
                    estado.setText(cliente.getEstado());
                    municipio.setText(cliente.getMunicipio());
                    localidadtxt.setText(cliente.getLocalidad());
                    montopagado.requestFocus();
                }else
                {
                    Toast.makeText(getBaseContext(), "Ocurrió un error al cargar los datos del Cliente.", Toast.LENGTH_SHORT).show();
                    nombretxt.setText("");
                    LimpiarDatos();
                }

            }
        });

        nombretxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_DEL)
                    LimpiarDatos();

                return false;
            }
        });

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
        //Guardar los datos y redireccionar a la vista
        if(cliente == null)
        {
            Toast.makeText(contexto, "Seleccione el cliente, y después registre la venta", Toast.LENGTH_SHORT).show();
            return;
        }

        Venta nuevaventa = new Venta();
        nuevaventa.setAuto(auto);
        nuevaventa.setCliente(cliente);
        nuevaventa.setMonto(Float.valueOf(montopagado.getText().toString()));

        //Llamar al WS que guardara los datos
        ResultadoService resultado = new AccesoDatosVentas(getBaseContext()).RegistrarVenta(nuevaventa);
        if(resultado.isRealizado())
        {
            Toast.makeText(getBaseContext(),"Se ha registrado la venta No: " + resultado.getIdReg(),Toast.LENGTH_SHORT).show();
            Intent ventanadatos = new Intent(VenderAutoActivity.this, DatosVentaActivity.class);
            ventanadatos.putExtra("Id", String.valueOf(resultado.getIdReg()));
            startActivity(ventanadatos);
            finish();
        }
        else
            Toast.makeText(getBaseContext(),"Ocurrió un error al intentar registrar la venta",Toast.LENGTH_SHORT).show();
    }

    private void LimpiarDatos()
    {
        domiciliotxt.setText("");
        estado.setText("");
        municipio.setText("");
        localidadtxt.setText("");
        cliente = null;
    }

}
