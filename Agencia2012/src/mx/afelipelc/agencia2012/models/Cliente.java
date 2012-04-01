package mx.afelipelc.agencia2012.models;

public class Cliente{
	
	int Id;
	String Nombre;
	String Apellidos;
	int Edad;
	int IdLocalidad;
	String Localidad;
	String Calle;
	String Numero;
	String Telefono;
	String Email;
	int IdMunicipio;
	String Municipio;
	int IdEstado;
	String Estado;	
	
//	public Cliente(int Id, String Nombre, String Apellidos)
//	{
//		this.id = Id;
//		this.nombre = Nombre;
//		this.apellidos = Apellidos;
//	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}

	public String getApellidos() {
		return Apellidos;
	}

	public void setApellidos(String apellidos) {
		this.Apellidos = apellidos;
	}

	public int getEdad() {
		return Edad;
	}

	public void setEdad(int edad) {
		this.Edad = edad;
	}

	public int getIdLocalidad() {
		return IdLocalidad;
	}

	public void setIdLocalidad(int IdLocalidad) {
		this.IdLocalidad = IdLocalidad;
	}

	public String getCalle() {
		return Calle;
	}

	public void setCalle(String calle) {
		this.Calle = calle;
	}

	public String getNumero() {
		return Numero;
	}

	public void setNumero(String numero) {
		this.Numero = numero;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		this.Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}
	
	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	public int getIdMunicipio() {
		return IdMunicipio;
	}

	public void setIdMunicipio(int idMunicipio) {
		IdMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return Municipio;
	}

	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}

	public int getIdEstado() {
		return IdEstado;
	}

	public void setIdEstado(int idEstado) {
		IdEstado = idEstado;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
}
