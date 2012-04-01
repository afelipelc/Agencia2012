package mx.afelipelc.agencia2012.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.*;
import mx.afelipelc.agencia2012.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import mx.afelipelc.agencia2012.accesodatos.AccesoDatosAutos;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorSpinner;
import mx.afelipelc.agencia2012.models.Auto;
import mx.afelipelc.agencia2012.models.MarcaAuto;
import mx.afelipelc.agencia2012.models.ResultadoService;

public class AgregarAutoActivity extends Activity {

	EditText noserie;

	Spinner marca;
	EditText modelo;
	EditText anio;
	EditText color;
	EditText precio;
	TextView vendido;
	
	//Barras de navegacion
	TextView tituloeditbar;
	View editbar;
	View navbar;
	Button cancelarbar;
	Button okbar;

    Button venderauto;
    Button verventa;

    int IdMarca;
    String NombreMarca;
    MarcaAuto[] marcas;
    Auto auto = new Auto();
    AccesoDatosAutos accesodatosautos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        venderauto.setVisibility(View.GONE);
        verventa.setVisibility(View.GONE);

		//Operacion sobre barras de navegacion
		//ocultar la barra de navegacion EditBar
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

		this.HabilitarTextBoxes(true);

        //Dialogo Yes NO
        final AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setMessage("¿Confirma registrar el nuevo auto?");
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

        //Inflar el Spinner de marcas
        marcas = accesodatosautos.MarcasAuto();
        if(marcas != null)
        {
            marca.setPrompt("Marca");
            marca.setAdapter(new AdaptadorSpinner(this,0,marcas));
        }

        //Al seleccionar elemento del spinner Marca
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
                return;
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
        //Guardar los datos y redireccionarlo a la vista de datos
        auto.setId(0);
        auto.setNoSerie(noserie.getText().toString());
        auto.setMarca(new MarcaAuto());
        auto.getMarca().setIdMarca(IdMarca);
        auto.getMarca().setNombre(NombreMarca);
        auto.setModelo(modelo.getText().toString());
        auto.setAnio(Integer.valueOf(anio.getText().toString()));
        auto.setColor(color.getText().toString());
        auto.setPrecio(Float.valueOf(precio.getText().toString()));
        //Llamar al WS que guardara los datos
        ResultadoService resultado = accesodatosautos.DatosAuto(auto, "RegistrarAuto");
        if(resultado.isRealizado())
        {
            Toast.makeText(getBaseContext(),"Se ha registrado el Auto: " + auto.getNoSerie(),Toast.LENGTH_SHORT).show();
            Intent ventanadatos = new Intent(AgregarAutoActivity.this, DatosAutoActivity.class);
            ventanadatos.putExtra("Id", resultado.getIdReg());
            startActivity(ventanadatos);
            finish();
        }
        else
            Toast.makeText(getBaseContext(),"Ocurrió un error al intentar registrar el auto: " + auto.getNoSerie(),Toast.LENGTH_SHORT).show();
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

}
