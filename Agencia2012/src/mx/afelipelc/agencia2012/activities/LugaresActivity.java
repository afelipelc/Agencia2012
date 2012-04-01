package mx.afelipelc.agencia2012.activities;

import android.widget.*;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.accesodatos.AccesoListaLugares;
import mx.afelipelc.agencia2012.adaptadores.AdaptadorLugares;
import mx.afelipelc.agencia2012.models.ElementoSimple;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LugaresActivity extends Activity {

	ListView listalugares;
	Button okbtn;
	Button cancelarbtn;
	TextView titulo;
    String mostrar;
    AutoCompleteTextView filtrolugares;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lugares);
		
		listalugares = (ListView) findViewById(R.id.ListaLugares);
		cancelarbtn = (Button) findViewById(R.id.CancelOptBtn);
		okbtn = (Button) findViewById(R.id.OkBtn);
		okbtn.setVisibility(View.GONE);
		titulo = (TextView) findViewById(R.id.TituloEditBar);
		titulo.setText("Seleccionar lugar");
        filtrolugares = (AutoCompleteTextView) findViewById(R.id.FiltroLugarAutoCompl);
		titulo = (TextView) findViewById(R.id.TituloEditBar);
		//Llamar el adaptador de lugares
		
		//mostrar la lista de lugares en base a la peticion
		Bundle extras = getIntent().getExtras();
		mostrar = extras.getString("mostrar");

		ElementoSimple[] lugares = null;
		Log.d("Mostrar en lugares: ", mostrar);
		if(mostrar.equals("estados"))
			Toast.makeText(this, "mostrar igual a:"+mostrar, Toast.LENGTH_SHORT).show();
		if(!mostrar.equals("")){
			AccesoListaLugares accesolugares = new AccesoListaLugares(this);
			if(mostrar.equals("estados"))
			{
				//cargar la lista de estados
                titulo.setText("Seleccionar Estado");
				lugares = accesolugares.ListaEstados();
				Log.d("Lista de estados: ", lugares.length + " elementos");
			}

            if(mostrar.equals("municipios"))
            {
                //cargar la lista de municipios del estado X
                titulo.setText("Seleccionar Municipio");
                int idestado = extras.getInt("idestado");
                lugares = accesolugares.MunicipiosEstado(idestado);
                Log.d("Lista de municipios: ", lugares.length + " elementos");
            }

            if(mostrar.equals("localidades"))
            {
                //cargar la lista de municipios del estado X
                titulo.setText("Seleccionar Localidad");
                int idmpio = extras.getInt("idmunicipio");
                lugares = accesolugares.LocalidadesMunicipio(idmpio);
                Log.d("Lista de localidades: ", lugares.length + " elementos");
            }

			if(lugares!=null)
			{
				AdaptadorLugares adaptador = new AdaptadorLugares(this, lugares);
				listalugares.setAdapter(adaptador);

                //filtrolugares.setAdapter(adaptador);

                //Llenar el autocompletetextview
                List<HashMap<String,String>> mapalugares = new ArrayList<HashMap<String, String>>();
                for(ElementoSimple lugar: lugares)
                {
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("Id",String.valueOf(lugar.getId()));
                    map.put("Nombre",lugar.getNombre());
                    mapalugares.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(this, mapalugares, R.layout.list_item_lugares, new String[]{"Id", "Nombre"}, new int[] {R.id.IdLugarLbl, R.id.NombreLugarLbl});
                filtrolugares.setAdapter(adapter);
			}
		}
		
		
		
		listalugares.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  //Toast.makeText(parent.getContext(), "Total elementos: " + listalugares.getCount() + ", posicion: " + id, Toast.LENGTH_SHORT).show();
                  //try{
		      	//Al seleccionar
		    	  //Toast.makeText(parent.getContext(), "Seleccionado >>> " + position, position ).show();

                  //int nums = listalugares.
                  //TextView nombrelugar = (TextView)item.findViewById(R.id.NombreLugarLbl);
//                  TextView nombrelugar = (TextView)view.findViewById(R.id.NombreLugarLbl);
//                          //Log.d("Lugares", "Se recupero el elemento view");
//                  TextView idlugar = (TextView)view.findViewById(R.id.IdLugarLbl);
//		    	  //Toast.makeText(parent.getContext(), "Seleccionado >>> Lugar " + idlugar.getText() + " nombre: "+nombrelugar.getText(), Toast.LENGTH_SHORT).show();
////		    	  Toast.makeText(parent.getContext(), "El ID del cliente seleccionado es >>> " + idcliente.getText(), position).show();
//
//		    	//Devolver parámetros que indiquen el lugar seleccionado
//		    	  //finish();
//		    	  Intent intent = new Intent();
//                    intent.putExtra("lugares", mostrar);
//                     intent.putExtra("idlugar", idlugar.getText());
//                     intent.putExtra("nombrelugar", nombrelugar.getText());
//		    	     setResult(RESULT_OK, intent);
//		    	     finish();
//                  }catch(Exception e)
//                  {
//                      Toast.makeText(parent.getContext(), "Posicion seleccionada: " + position , Toast.LENGTH_SHORT).show();
//                  }

                  LugarSeleccionado(view);
		      }
		    });

        filtrolugares.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarSeleccionado(view);
            }
        });

        this.cancelarbtn.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v)
	        {
	        	//Devolver un parámetro que indique que se canceló la selección
	        	finish();
	        }
        });
	}

    private void LugarSeleccionado(View view)
    {
        TextView nombrelugar = (TextView)view.findViewById(R.id.NombreLugarLbl);
        TextView idlugar = (TextView)view.findViewById(R.id.IdLugarLbl);
        filtrolugares.setText("");

        Intent intent = new Intent();
        intent.putExtra("lugares", mostrar);
        intent.putExtra("idlugar", idlugar.getText());
        intent.putExtra("nombrelugar", nombrelugar.getText());
        setResult(RESULT_OK, intent);
        finish();
    }

}

