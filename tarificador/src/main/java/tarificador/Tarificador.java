package tarificador;

public class Tarificador implements TarificadorBoundary {
	
	public double calcularCostoLlamada(RegistroCDR registro) {
		CalculadorDeCosto calculador = this.crearCalculador(registro);
		double res = calculador.calcularCosto(registro);
		registro.setCosto(res);
		return res;
	} 

	public CalculadorDeCosto crearCalculador(RegistroCDR registro) {
		ListaClientes LC= ListaClientes.getInstance();
		String plan = LC.getPlanDeCliente(registro.getTelefonoOrigen());
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
