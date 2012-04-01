package mx.afelipelc.agencia2012.accesodatos;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.Cliente;
import mx.afelipelc.agencia2012.models.ElementoSimple;

import android.app.Activity;
import android.content.res.Resources;

import com.google.gson.Gson;
import mx.afelipelc.agencia2012.models.ResultadoService;

public final class AccesoDatosClientes {

	//Context context;
	Resources res;
	
	ElementoSimple[] listaclientes = null;
	AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();

    private  String URL_WebServices;
	public AccesoDatosClientes(Context context)
	{
		//this.context = context;
		res = context.getResources();
        this.URL_WebServices = ((Agencia2012) context.getApplicationContext()).getUrlServicios();

        /*
        * Resto de codigo que ya se tenga incluido en el constructor
         */
        //si se tiene un usuario valido y autentificado, pasar la clave a la clase que accede a los WS
        if(((Agencia2012) context.getApplicationContext()).getUsuario()!= null && ((Agencia2012) context.getApplicationContext()).getUsuario().isAutentificado())
            acesoservicio.KeyAuth = ((Agencia2012) context.getApplicationContext()).getUsuario().getKeyAuth();
	}
	
	public final ElementoSimple[] ListaClientes(){
		try {
		//acceder al servicio
		//String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "ListaClientes", res.getString(R.string.UrlWS) + res.getString(R.string.ClientesWS), null);
        String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "ListaClientes", URL_WebServices + res.getString(R.string.ClientesWS), null);
    	if(!CadenaJSON.equals(""))
    	{
		Gson gson = new Gson();
		listaclientes = gson.fromJson(CadenaJSON, ElementoSimple[].class);
        
    	return listaclientes;  
    	}else
    		return null;
		}catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

    public final ElementoSimple[] BuscarClientes(String q){
        try {
            //acceder al servicio
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("q", q);

            //Log.d("Busqueda", "Buscando clientes con: " + q);

            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "BuscarClientes", URL_WebServices + res.getString(R.string.ClientesWS), parametros);
            //Log.d("Resultado", "Resultado: " + CadenaJSON);

            if(!CadenaJSON.equals("") && CadenaJSON.length() > 12)
            {
                Gson gson = new Gson();
                listaclientes = gson.fromJson(CadenaJSON, ElementoSimple[].class);

                return listaclientes;
            }else
                return null;

        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

	public final Cliente DatosCliente(int IdCliente){
		try {
			//crear los parametros
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("IdCliente", String.valueOf(IdCliente));

			//acceder al servicio
			//String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "DatosCliente", res.getString(R.string.UrlWS)+res.getString(R.string.ClientesWS), parametros);
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "DatosCliente", URL_WebServices +res.getString(R.string.ClientesWS), parametros);
	    	if(!CadenaJSON.equals(""))
	    	{
			Gson gson = new Gson();
	    	Cliente cliente = gson.fromJson(CadenaJSON, Cliente.class);

	    	return cliente;
	    	}else
	    		return null;
			}catch (Exception e) {
				//e.printStackTrace();
				return null;
			}         
	}

    public final ResultadoService DatosCliente(Cliente cliente, String Accion){
        try {
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdCliente", String.valueOf(cliente.getId()));
            parametros.put("Nombre", cliente.getNombre());
            parametros.put("Apellidos", cliente.getApellidos());
            parametros.put("Edad", String.valueOf(cliente.getEdad()));
            parametros.put("IdLocalidad", String.valueOf(cliente.getIdLocalidad()));
            parametros.put("Calle", cliente.getCalle());
            parametros.put("Numero", cliente.getNumero());
            parametros.put("Telefono", cliente.getTelefono());
            parametros.put("Email", cliente.getEmail());

            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), Accion, URL_WebServices +res.getString(R.string.ClientesWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                ResultadoService resultadoservice = new Gson().fromJson(CadenaJSON, ResultadoService.class);

                return  resultadoservice;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

}
