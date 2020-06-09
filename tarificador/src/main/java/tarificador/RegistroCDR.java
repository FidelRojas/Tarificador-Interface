package tarificador;

public class RegistroCDR {
	private String telefonoOrigen;
	private String telefonoDestino;
	private String fecha;
	private String hora;
	private int tiempoDuracionSegundo;
	private double costo;
	
	public RegistroCDR() {
	}
	
	public RegistroCDR (String telefonoOrigen, String telefonoDestino, String fecha, String hora, int tiempoDuracionSegundo) {
		this.telefonoOrigen=telefonoOrigen;
		this.telefonoDestino=telefonoDestino;
		this.fecha=fecha;
		this.hora=hora;
		this.tiempoDuracionSegundo=tiempoDuracionSegundo;
	}
	
	public String getTelefonoOrigen() {
		return telefonoOrigen;
	}
	public void setTelefonoOrigen(String telefonoOrigen) {
		this.telefonoOrigen = telefonoOrigen;
	}
	public String getTelefonoDestino() {
		return telefonoDestino;
	}
	public void setTelefonoDestino(String telefonoDestino) {
		this.telefonoDestino = telefonoDestino;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getTiempoDuracionSegundos() {
		return tiempoDuracionSegundo;
	}
	public void setTiempoDuracionSegundos(int tiempoDuracionSegundo) {
		this.tiempoDuracionSegundo = tiempoDuracionSegundo;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String retornarCadenaCDR(String separador){
		String cadena= telefonoOrigen+separador+telefonoDestino+separador+fecha +separador+hora+separador+tiempoDuracionSegundo+separador+costo;
		return cadena;
	}
}
