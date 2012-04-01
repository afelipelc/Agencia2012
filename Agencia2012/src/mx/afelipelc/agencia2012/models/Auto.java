package mx.afelipelc.agencia2012.models;

public class Auto {

	int IdAuto;
	String NoSerie;
	MarcaAuto Marca;
	String Modelo;
	int Anio;
	String Color;
	Float Precio;
	boolean Vendido;
    int IdVenta;

	public int getId() {
		return IdAuto;
	}

	public void setId(int id) {
		this.IdAuto = id;
	}

	public String getNoSerie() {
		return NoSerie;
	}

	public void setNoSerie(String noSerie) {
		this.NoSerie = noSerie;
	}

	public MarcaAuto getMarca() {
		return Marca;
	}

	public void setMarca(MarcaAuto marca) {
		this.Marca = marca;
	}

	public String getModelo() {
		return Modelo;
	}

	public void setModelo(String modelo) {
		this.Modelo = modelo;
	}

	public int getAnio() {
		return Anio;
	}

	public void setAnio(int anio) {
		this.Anio = anio;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		this.Color = color;
	}

	public Float getPrecio() {
		return Precio;
	}

	public void setPrecio(Float precio) {
		this.Precio = precio;
	}

	public boolean isVendido() {
		return Vendido;
	}

	public void setVendido(Boolean vendido) {
		this.Vendido = vendido;
	}

    public int getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(int idVenta) {
        IdVenta = idVenta;
    }
}
