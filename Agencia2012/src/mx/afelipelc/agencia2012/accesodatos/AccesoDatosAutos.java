package mx.afelipelc.agencia2012.accesodatos;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.gson.Gson;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.Auto;
import mx.afelipelc.agencia2012.models.MarcaAuto;
import mx.afelipelc.agencia2012.models.ResultadoService;

import java.util.HashMap;
import java.util.Map;

public class AccesoDatosAutos {
    //Context context;
    Resources res;

    Auto[] listaautos = null;
    AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();

    private  String URL_WebServices;

    public  AccesoDatosAutos(Context context)
    {
        //this.context = context;
        res = context.getResources();
        this.URL_WebServices = ((Agencia2012) context.getApplicationContext()).getUrlServicios();
    }

    public final Auto[] ListaAutos()
    {
        try {
            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "ListaAutos", URL_WebServices + res.getString(R.string.AutosWS), null);
            if(!CadenaJSON.equals(""))
            {
                //Log.d("Respuesta del WS", CadenaJSON);
                //Gson gson = new Gson();
                //listaautos = gson.fromJson(CadenaJSON, Auto[].class);
                return  new Gson().fromJson(CadenaJSON, Auto[].class);
                //return listaautos;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public  final Auto DatosAuto(int IdAuto)
    {
        try{
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdAuto", String.valueOf(IdAuto));

            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "DatosAuto", URL_WebServices +res.getString(R.string.AutosWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                Gson gson = new Gson();
                //Auto auto = gson.fromJson(CadenaJSON, Auto.class);
                return  new Gson().fromJson(CadenaJSON, Auto.class);
                //return auto;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public final ResultadoService DatosAuto(Auto auto, String Accion)
    {
        try{
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdAuto", String.valueOf(auto.getId()));
            parametros.put("NoSerie", auto.getNoSerie());
            parametros.put("IdMarca", String.valueOf(auto.getMarca().getIdMarca()));
            parametros.put("Modelo", auto.getModelo());
            parametros.put("Anio", String.valueOf(auto.getAnio()));
            parametros.put("Color", auto.getColor());
            parametros.put("Precio", String.valueOf(auto.getPrecio()));

            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), Accion, URL_WebServices +res.getString(R.string.AutosWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                //ResultadoService resultadoservice = new Gson().fromJson(CadenaJSON, ResultadoService.class);
                return new Gson().fromJson(CadenaJSON, ResultadoService.class);
                //return  resultadoservice;
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
    
    public final Auto[] BuscarAutos(String q)
    {
        try
        {
            //acceder al servicio
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("q", q);

            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "BuscarAutos", URL_WebServices + res.getString(R.string.AutosWS), parametros);
            if(!CadenaJSON.equals("") && CadenaJSON.length() > 12)
            {
                //Gson gson = new Gson();
                //listaautos = gson.fromJson(CadenaJSON, Auto[].class);
                return new Gson().fromJson(CadenaJSON, Auto[].class);
                //return  listaautos;
            }else
                return  null;
        }
        catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
    
    public final MarcaAuto[] MarcasAuto()
    {
        try {
            //acceder al servicio
            MarcaAuto[] listamarcas;
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "MarcasAutos", URL_WebServices + res.getString(R.string.AutosWS), null);
            if(!CadenaJSON.equals(""))
            {
                //Gson gson = new Gson();
                //listamarcas = gson.fromJson(CadenaJSON, MarcaAuto[].class);
                return new Gson().fromJson(CadenaJSON, MarcaAuto[].class);
            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}