package tarificador;

import java.util.ArrayList;
import java.util.HashMap;

public class Central {
	HashMap<String, String> configuraciones;
	private TarificadorBoundary tarificador;
	private ArrayList<RegistroCDR> CDRsCargados;
	public Central() {
		tarificador = new Tarificador();
		ListaClientes LC= ListaClientes.getInstance();
		configuraciones = new HashMap<String, String>();
		configuraciones.put("persistencia", "guardarATxt");
	}
	public void cargarCDRsDesdeTexto(String path) {
//		TODO
		FileCDRRepository FileRepo = new FileCDRRepository(path);
		CDRsCargados = FileRepo.getList();
		
	}
	
	public void debugMostrar() {
		FileCDRRepository FileRepo = new FileCDRRepository();
		for(RegistroCDR registro : CDRsCargados) {
			System.out.println(FileRepo.tranformCDRtoString(registro));
		}
	}
	
	public double tarificarCDR(RegistroCDR registro) {
		tarificador.setRegistro(registro);
		return tarificador.calcularCostoLlamada();
	}
	
	public void tarificarUnaListaDeCDRs(ArrayList<RegistroCDR> registros) {
		for(RegistroCDR registro : registros) {
			tarificador.setRegistro(registro);
			tarificador.calcularCostoLlamada();
		}
	}
	
	public ArrayList<RegistroCDR> tarificarCDRsCargados() {
		for(RegistroCDR registro : CDRsCargados) {
			tarificador.setRegistro(registro);
			tarificador.calcularCostoLlamada();
		}
		return CDRsCargados;
	}
	
	public double facturarCliente(Cliente cliente) {
		return 0.0;
	}
	
	public void cambiarConfiguracion(String key, String value) {
		System.out.println(this.configuraciones.get(key));
		this.configuraciones.replace(key, value);
		System.out.println(this.configuraciones.get(key));

	}
	
	public void guardarResultados() {
		if(configuraciones.get("persistencia")== "guardarATxt") {
			
		}
		else if(configuraciones.get("persistencia")=="guardarABaseDatos") {
			
		}
	}
	

	
}
