package mx.afelipelc.agencia2012.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import mx.afelipelc.agencia2012.Agencia2012;
import mx.afelipelc.agencia2012.R;
import mx.afelipelc.agencia2012.data.Agencia2012DataSource;
import mx.afelipelc.agencia2012.data.RegistroConfig;

public class ConfiguracionActivity extends Activity {
    private  Agencia2012DataSource datosdb;
    Button cancelarbtn, okbtn;
    TextView titulo;
    EditText urlservicios;
    RegistroConfig registroservices;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.configuracion);

        cancelarbtn = (Button) findViewById(R.id.CancelOptBtn);
        okbtn = (Button) findViewById(R.id.OkBtn);
        titulo = (TextView) findViewById(R.id.TituloEditBar);
        titulo.setText("Configuración");
        urlservicios = (EditText) findViewById(R.id.URLServicios);

        //Cargar los elementos de configuracion
        datosdb = new Agencia2012DataSource(this);
        datosdb.Open();

        registroservices = datosdb.GetRegistro("urlservicios");
        if(registroservices!=null)
            urlservicios.setText(registroservices.getValor());
        else
        {
            Toast.makeText(getApplicationContext(),"Error al cargar los datos de la BD.",Toast.LENGTH_SHORT).show();
            finish();
        }

        datosdb.Close();
        this.cancelarbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
              finish();
            }
        });

        this.okbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                datosdb.Open();
                //guardar los datos
                if(registroservices != null)
                {
                    registroservices.setValor(urlservicios.getText().toString());
                    //Actualizar la variable Global (URLServicios)
                    ((Agencia2012) getApplication()).setUrlServicios(registroservices.getValor());

                    Log.d("La nueva URL de WS", ((Agencia2012) getApplication()).getUrlServicios());

                    if(datosdb.ActualizaRegistro(registroservices))
                        Toast.makeText(getApplicationContext(),"Datos guardados correctamente.",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"No se pudo actualizar los datos.",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(),"No se guardó ningún dato.",Toast.LENGTH_SHORT).show();

                datosdb.Close();

                finish();
            }
        });

    }
}