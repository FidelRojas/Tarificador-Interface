package tarificador;

public class Tarificador {
	private RegistroCDR registro;
	
	public double calcularCostoLlamada() {
		CalculadorDeCosto calculador = this.crearCalculador();
		double res = calculador.calcularCosto(registro);
		registro.setCosto(res);
		return res;
	} 
	
	public void setRegistro(RegistroCDR registro) {
		this.registro = registro;
	}
	
	private CalculadorDeCosto crearCalculador() {
		ListaClientes LC= ListaClientes.getInstance();
		Cliente cliente = LC.buscar(this.registro.getTelefonoOrigen());
		String plan = cliente.getPlan();
		if (plan == "Wow") {
			CalculadorDeCosto calculador = new CalculadorPlanWow();
			return calculador;
		}
		if (plan == "PostPago") {
			CalculadorDeCosto calculador = new CalculadorPlanPostpago();
			return calculador;
		}
		if (plan == "PrePago") {
			CalculadorDeCosto calculador = new CalculadorPlanPrepago();
			return calculador;
		}
		return null;
	}
}
