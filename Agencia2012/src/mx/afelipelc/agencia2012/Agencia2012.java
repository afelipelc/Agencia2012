package mx.afelipelc.agencia2012;

import android.app.Application;
import mx.afelipelc.agencia2012.models.Usuario;

public class Agencia2012 extends Application{

    private  String urlservicios;

    private Usuario usuario;

    public String getUrlServicios() {
        return urlservicios;
    }

    public void setUrlServicios(String UrlServicios) {
        this.urlservicios = UrlServicios;
    }

    public  Agencia2012()
    {
        super();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Otros campos, Getter's y Setter's
}
