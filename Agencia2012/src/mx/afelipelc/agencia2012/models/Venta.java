package mx.afelipelc.agencia2012.models;

import java.util.Date;


public class Venta {

	int IdVenta;
	String Fecha;
    Float Monto;
	Cliente Cliente;
	Auto Auto;

	public int getIdVenta() {
		return IdVenta;
	}
	public void setIdVenta(int IdVenta) {
		this.IdVenta = IdVenta;
	}
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String Fecha) {
		this.Fecha = Fecha;
	}
    public Float getMonto() {
        return Monto;
    }

    public void setMonto(Float monto) {
        Monto = monto;
    }
	public Cliente getCliente() {
		return Cliente;
	}
	public void setCliente(Cliente Cliente) {
		this.Cliente = Cliente;
	}

	public Auto getAuto() {
		return Auto;
	}

	public void setAuto(Auto Auto) {
		this.Auto = Auto;
	}
}
