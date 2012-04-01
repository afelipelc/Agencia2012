package mx.afelipelc.agencia2012.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.accesodatos.AccesoAutentificacion;
import mx.afelipelc.agencia2012.models.Usuario;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: afelipelc
 * Date: 21/03/12
 * Time: 12:51 PM
 */
public class LoginActivity extends Activity {

    Usuario usuario;

    Button IniciarSesionBtn;
    EditText NombreUsuarioTxt;
    EditText PasswordTxt;

    AccesoAutentificacion accesoautentificacion;

    //elemento para el menu
    private final int MENU_CONF=1;
    private final int GROUP_DEFAULT=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Inicializar el objeto que acceder al WS de Autentificacion
        accesoautentificacion = new AccesoAutentificacion(this);

        IniciarSesionBtn = (Button) findViewById(R.id.IniciarSesionBtn);
        NombreUsuarioTxt = (EditText) findViewById(R.id.NombreUsuarioTxt);
        PasswordTxt = (EditText) findViewById(R.id.PasswordTxt);

        //Mensaje de notificacion, en lugar de utilizar Toast
        final AlertDialog.Builder notificacion = new AlertDialog.Builder(this);
        notificacion.setIcon(android.R.drawable.ic_dialog_alert);
        notificacion.setTitle("Error");
        //Boton Aceptar en cuadro de dialogo
        notificacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });


        IniciarSesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificar que los datos se hayan ingresado
                if(NombreUsuarioTxt.getText().toString().equals("") || PasswordTxt.getText().toString().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Ingrese el nombre de usuario y contraseña",Toast.LENGTH_SHORT).show();
                    return;
                }

                //llamar al web service para autentifificar al usuario
                usuario = accesoautentificacion.ValidarUsuario(NombreUsuarioTxt.getText().toString(), PasswordTxt.getText().toString());

                //Si el objeto usuario es null, significa que no se pudo conectar al servidor
                if(usuario == null)
                {   //notificar al usuario
                    notificacion.setMessage("No se pudo conectar al servidor, verifique que la URL sea correcta, presiones Menu para acceder a la configuración.");
                    notificacion.show();
                    return;
                }

                //si se ha conectado, verificar si es usuario valido
                if(usuario.isAutentificado())
                {   //Si es usuario valido
                    //guardar el objeto usuario en el contexto de la aplicacion
                    ((Agencia2012) getApplication()).setUsuario(usuario);

                    //Ir a la actividad principal
                    Intent ventanaprincipal = new Intent(LoginActivity.this, Home.class);
                    startActivity(ventanaprincipal);
                    Toast.makeText(getBaseContext(),"Bienvenid@ <"+ usuario.getNombreUsuario()+">",Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {   //Si es usuario invalido, notificar al usuario
                    notificacion.setMessage("Error al iniciar sesión, el nombre de usuario o contraseña son incorrectos, intente nuevamente.");
                    notificacion.show();
                }
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        //reiniciar ob objeto que conecta al WS por si se cambio la URL
        accesoautentificacion = new AccesoAutentificacion(this);
        //Toast.makeText(this,"Volviendo a la actividad",Toast.LENGTH_SHORT).show();
    }
    //el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(GROUP_DEFAULT, MENU_CONF, 0, "Configuración")
                .setIcon(R.drawable.config_img);
        return super.onCreateOptionsMenu(menu);
    }

    //Al seleccionar una opcion del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case MENU_CONF:
                //Abrir el activity de configuracion
                Intent ventanaconfig = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                startActivity(ventanaconfig);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}