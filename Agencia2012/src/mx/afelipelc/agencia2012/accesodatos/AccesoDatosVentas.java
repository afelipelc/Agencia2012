package mx.afelipelc.agencia2012.accesodatos;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.gson.Gson;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.ResultadoService;
import mx.afelipelc.agencia2012.models.Venta;
import mx.afelipelc.agencia2012.models.VentaSimple;

import java.util.HashMap;
import java.util.Map;

public class AccesoDatosVentas {
    Resources res;
    AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();
    private  String URL_WebServices;
    public AccesoDatosVentas(Context context)
    {
        res = context.getResources();
        this.URL_WebServices = ((Agencia2012) context.getApplicationContext()).getUrlServicios();
    }

    public final VentaSimple[] ListaVentas()
    {
        try {
            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "ListaVentas", URL_WebServices + res.getString(R.string.VentasWS), null);
            if(!CadenaJSON.equals(""))
            {
               return new Gson().fromJson(CadenaJSON, VentaSimple[].class);
                //return listaventas;
            }else
                return null;

        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }

    public final Venta DatosVenta(int IdVenta)
    {
        try{
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdVenta", String.valueOf(IdVenta));
            //acceder al servicio
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "DatosVenta", URL_WebServices +res.getString(R.string.VentasWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                //Log.d("Resultado Venta >> " + IdVenta, CadenaJSON);
                return new Gson().fromJson(CadenaJSON, Venta.class);
            }else
                return  null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public final ResultadoService RegistrarVenta(Venta venta)
    {
        try{
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("IdAuto", String.valueOf(venta.getAuto().getId()));
            parametros.put("IdCliente", String.valueOf(venta.getCliente().getId()));
            parametros.put("MontoPagado", String.valueOf(venta.getMonto()));

            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "RegistrarVenta", URL_WebServices +res.getString(R.string.VentasWS), parametros);
            if(!CadenaJSON.equals(""))
            {
                return new Gson().fromJson(CadenaJSON, ResultadoService.class);

            }else
                return null;
        }catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}

