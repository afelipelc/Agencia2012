package mx.afelipelc.agencia2012.accesodatos;

import java.util.Map;
import java.util.Map.Entry;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import org.kxml2.kdom.Element;

public class AccesoServiciosWeb {

    //Campo que almacena la clave de autentificacion
    public String KeyAuth="";

	//Método que invocará al WS y devolverá respuesta como String
	public String AccesoServicio(String EspacioNombres, String Metodo, String URL, Map<String, String> parametros)
	{
		//Constantes de acceso al web service
		String soap_accion = EspacioNombres +"/"+Metodo;

		SoapObject request;
		SoapSerializationEnvelope envelope;
		SoapPrimitive  resultsRequestSOAP=null;
		request = new SoapObject(EspacioNombres, Metodo);
		
		Log.d("Llamando a servicio: metodo", Metodo);
		Log.d("Url Servicio: ", URL);
		
		//antes de iniciar la llamada, agregar los parametros (solo si se especificaron)
		if(parametros != null){
            for (Entry<String, String> parametro : parametros.entrySet()) {
                request.addProperty(parametro.getKey(), parametro.getValue());                
                Log.d("Parámetro de servicio>>", parametro.getKey() + ": valor=" + parametro.getValue());
            }
        }
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE transporte = new HttpTransportSE(URL);

		String  resultado = "";

        //si se tiene la clave de autentificacion, agregarla al encabezado Authentication de la solicitud
        if (!KeyAuth.equals(""))
        {
            //Creamos la lista de elementos a enviar en el encabezado de la solicitud
            Element[] headers = new Element[1];

            //Creamos el lemento principal  CredencialUsuario (lo encontramos en el ejemplo de solicitud del metodo
            // del WS que requiere un usuario autentificado)
            Element AuthHeader = new Element();
            AuthHeader.setName("CredencialUsuario");
            AuthHeader.setNamespace(EspacioNombres);

            //Creamos el elemento (hijo) KeyAuth que contiene la clave de autentificacion
            Element element = new Element();
            element.setName("KeyAuth");
            element.setNamespace(EspacioNombres);
            element.addChild(Element.TEXT, KeyAuth);
            AuthHeader.addChild(Element.ELEMENT, element);
            // almacenamos nuestro elemento AuthHeader en el arreglo de Elementos
            headers[0] = AuthHeader;
            //Asignamos los elementos de encabezado a la solicitud
            envelope.headerOut = headers;
        }

        //Log.d("Preparando","Preparando la llamada, encabezado Authentication: " + KeyAuth);

		//iniciar la llamada al web service		
		try {
            transporte.call(soap_accion, envelope);
			resultsRequestSOAP = (SoapPrimitive)envelope.getResponse();
			resultado = resultsRequestSOAP.toString();

            Log.d("Resultado Metodo>>", resultado);
		} catch (Exception e) {
			e.printStackTrace();
			return resultado;
		}
		
		return resultado;
	}
}
