package tarificador;

public class Historial {
	private int id;
	private String fechaHora;
	
	public void setId(int id) {
		this.id = id;
	}
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	public int getId() {
		return id;
	}
	public String getFechaHora() {
		return fechaHora;
	}
}
