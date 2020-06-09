package tarificador;

import java.util.HashMap;

public class Central {
	HashMap<String, String> configuraciones;
	private TarificadorBoundary tarificador;
	public Central() {
		tarificador = new Tarificador();
		ListaClientes LC= ListaClientes.getInstance();
		configuraciones = new HashMap<String, String>();
		configuraciones.put("persistencia", "guardarATxt");
	}
	
	public double tarificarCDR(RegistroCDR registro) {
		tarificador.setRegistro(registro);
		return tarificador.calcularCostoLlamada();
	}
	
	public double facturarCliente(Cliente cliente) {
		return 0.0;
	}
	
	public void cambiarConfiguracion(String key, String value) {
		System.out.println(this.configuraciones.get(key));
		this.configuraciones.replace(key, value);
		System.out.println(this.configuraciones.get(key));

	}
	
	
}
