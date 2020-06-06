package tarificador;

public class Central {
	private TarificadorBoundary tarificador;

	public Central() {
		tarificador = new Tarificador();
		ListaClientes LC= ListaClientes.getInstance();
	}
	
	public double tarificarCDR(RegistroCDR registro) {
		tarificador.setRegistro(registro);
		return tarificador.calcularCostoLlamada();
	}
	
	public double facturarCliente(Cliente cliente) {
		return 0.0;
	}
	
}
