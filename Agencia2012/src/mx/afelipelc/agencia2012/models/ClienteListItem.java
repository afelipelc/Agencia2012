package mx.afelipelc.agencia2012.models;

public class ClienteListItem {

	private int id;
	private String nombre;
	
	public ClienteListItem(int Id, String Nombre)
	{
		this.id = Id;
		this.nombre = Nombre;
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
}
