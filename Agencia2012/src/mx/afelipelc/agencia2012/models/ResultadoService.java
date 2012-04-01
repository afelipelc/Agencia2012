package mx.afelipelc.agencia2012.models;

public class ResultadoService {
    boolean Error;
    boolean Realizado;
    int IdReg;
    String Mensaje;

    public boolean isError() {
        return Error;
    }

    public void setError(boolean error) {
        Error = error;
    }

    public boolean isRealizado() {
        return Realizado;
    }

    public void setRealizado(boolean realizado) {
        Realizado = realizado;
    }

    public int getIdReg() {
        return IdReg;
    }

    public void setIdReg(int idReg) {
        IdReg = idReg;
    }

	public String getMensaje() {
		return Mensaje;
	}

	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}
}
