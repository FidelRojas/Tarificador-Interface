package tarificador;

public class Tarificador implements TarificadorBoundary {
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
	
	public CalculadorDeCosto crearCalculador() {
		ListaClientes LC= ListaClientes.getInstance();
		String plan = LC.getPlanDeCliente(this.registro.getTelefonoOrigen());
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
