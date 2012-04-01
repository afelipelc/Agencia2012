package mx.afelipelc.agencia2012.accesodatos;

import android.content.Context;
import android.content.res.Resources;
import com.google.gson.Gson;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.models.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: afelipelc
 * Date: 21/03/12
 * Time: 01:22 PM
 */
public class AccesoAutentificacion {
    //Context context;
    Resources res;
    Usuario usuario;

    AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();

    private  String URL_WebServices;
    public AccesoAutentificacion(Context context)
    {
        res = context.getResources();
        this.URL_WebServices = ((Agencia2012) context.getApplicationContext()).getUrlServicios();
    }

    public final Usuario ValidarUsuario(String NombreUsuario, String Password)
    {
        try {
            //crear los parametros
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("NombreUsuario", NombreUsuario);
            parametros.put("Password",Password);

            //Llamar al metodo del WS
            //String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "Validar", res.getString(R.string.UrlWS) + res.getString(R.string.LoginWS), parametros);
            String CadenaJSON = acesoservicio.AccesoServicio(res.getString(R.string.EspacioNombresWS), "Validar", URL_WebServices + res.getString(R.string.LoginWS), parametros);

            
            if(!CadenaJSON.equals("") && CadenaJSON.length() > 12)
            {   //deserializar el objeto Usuario
                usuario = new Gson().fromJson(CadenaJSON, Usuario.class);
                //Log.d("Usuario deserializado",usuario.getNombreUsuario() + " -- " + usuario.getKeyAuth());
                return usuario;
            } else
                return null;

        }catch (Exception e) {
            return null;
        }
    }
}
