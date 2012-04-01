package mx.afelipelc.agencia2012.models;

public class Lugar {
	
	int id;
	String nombre;
	
	public Lugar(int Id, String Nombre)
	{
		this.id = Id;
		this.nombre = Nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int Id) {
		this.id = Id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String Nombre) {
		this.nombre = Nombre;
	}
}