package mx.afelipelc.agencia2012.accesodatos;

import android.content.Context;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.ElementoSimple;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public class AccesoListaLugares {

	//Context context;
	Resources res;
	
	AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();
    private  String URL_WebServices;

	public AccesoListaLugares(Context context)
	{
		//this.context = context;
		res = context.getResources();
        this.URL_WebServices = ((Agencia2012) context.getApplicationContext()).getUrlServicios();
	}
	
	public final ElementoSimple[] ListaEstados(){
		try {
		//acceder al servicio
		String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "Estados", URL_WebServices + res.getString(R.string.LugaresWS), null);
    	if(!CadenaJSON.equals(""))
    	{
		Gson gson = new Gson();
		ElementoSimple[] listalugares = gson.fromJson(CadenaJSON, ElementoSimple[].class);
        
    	return listalugares;  
    	}else
    		return null;
		}catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

    public final ElementoSimple[] MunicipiosEstado(int IdEstado){
        try {
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdEstado", String.valueOf(IdEstado));

            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "MunicipiosEdo ", URL_WebServices + res.getString(R.string.LugaresWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                Gson gson = new Gson();
                ElementoSimple[] listalugares = gson.fromJson(CadenaJSON, ElementoSimple[].class);

                return listalugares;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public final ElementoSimple[] LocalidadesMunicipio(int IdMunicipio){
        try {
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdMunicipio", String.valueOf(IdMunicipio));

            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "LocalidadesMunicipio ", URL_WebServices + res.getString(R.string.LugaresWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                Gson gson = new Gson();
                ElementoSimple[] listalugares = gson.fromJson(CadenaJSON, ElementoSimple[].class);

                return listalugares;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
	
}
