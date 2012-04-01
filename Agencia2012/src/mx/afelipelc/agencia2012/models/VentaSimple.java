package mx.afelipelc.agencia2012.models;

import java.util.Date;

public class VentaSimple {
    private int IdVenta;
    private String NoSerieAuto;
    private String Cliente;
    private String Fecha;


    public int getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(int idVenta) {
        IdVenta = idVenta;
    }

    public String getNoSerieAuto() {
        return NoSerieAuto;
    }

    public void setNoSerieAuto(String noSerieAuto) {
        NoSerieAuto = noSerieAuto;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
