package mx.afelipelc.agencia2012.models;

/**
 * Created by IntelliJ IDEA.
 * User: afelipelc
 * Date: 21/03/12
 * Time: 12:52 PM
 * Class for store User Data logged into our application.
 */
public class Usuario {
    String NombreUsuario;
    boolean Autentificado;
    String KeyAuth;
    String[] Roles;

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public boolean isAutentificado() {
        return Autentificado;
    }

    public void setAutentificado(boolean autentificado) {
        Autentificado = autentificado;
    }

    public String getKeyAuth() {
        return KeyAuth;
    }

    public void setKeyAuth(String keyAuth) {
        KeyAuth = keyAuth;
    }

    public String[] getRoles() {
        return Roles;
    }

    public void setRoles(String[] roles) {
        Roles = roles;
    }

    //Metodo para comprobar si el usuario tiene un Rol X
    public boolean IsInRole(String Rol)
    {
        boolean resultado = false;
        for(String rol : Roles)
        {
            if(rol.equals(Rol))
            {
                resultado = true;
                break;
            }
        }
        return  resultado;
    }
}
