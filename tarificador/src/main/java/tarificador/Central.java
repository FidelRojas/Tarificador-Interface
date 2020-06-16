package tarificador;

import java.util.ArrayList;
import java.util.HashMap;

public class Central {
	HashMap<String, String> configuraciones;
	private TarificadorBoundary tarificador;
	private ArrayList<RegistroCDR> CDRsCargados;
	private RepositoryBoundary repositorio =null;
	
	public Central() {
		tarificador = new Tarificador();
		// ListaClientes LC= ListaClientes.getInstance();
		configuraciones = new HashMap<String, String>();
		configuraciones.put("persistencia", "SQL");
		this.repositorio = new SQLiteCDRRepository();
	}
	public void cargarCDRsDesdeTexto(String path) {
		FileCDRRepository FileRepo = new FileCDRRepository(path);
		CDRsCargados = FileRepo.getList();
		
	}
	
	public void debugMostrar() {
		FileCDRRepository FileRepo = new FileCDRRepository();
		for(RegistroCDR registro : CDRsCargados) {
			System.out.println(FileRepo.transformarCDRaString(registro));
		}
	}
	
	public double tarificarCDR(RegistroCDR registro) {
		return tarificador.calcularCostoLlamada(registro);
	}
	
	public void tarificarUnaListaDeCDRs(ArrayList<RegistroCDR> registros) {
		for(RegistroCDR registro : registros) {
			tarificador.calcularCostoLlamada(registro);
		}
	}
	
	public ArrayList<RegistroCDR> tarificarCDRsCargados() {
		for(RegistroCDR registro : CDRsCargados) {
			tarificador.calcularCostoLlamada(registro);
		}
		return CDRsCargados;
	}
	
	public ArrayList<RegistroCDR> getCdrsCargados() {
		return CDRsCargados;
	}
	
	public void borrarCDRsCargados() {
		this.CDRsCargados.clear();
	}
	
	public double facturarCliente(Cliente cliente) {
		return 0.0;
	}
	
	public void cambiarConfiguracion(String key, String value) {
		if(value.equals("TXT")) {
			repositorio = new FileCDRRepository();
			this.configuraciones.replace(key, value);
		}
		else if(value.equals("SQL")) {
			repositorio = new SQLiteCDRRepository();
			this.configuraciones.replace(key, value);
		}
	}
	
	public ArrayList<Historial> getHistorial(){
		return repositorio.obtenerHistorialDeTarificaciones();
	}
	
	public ArrayList<RegistroCDR> obtenerCDRsDeUnHistorial(Historial historial){
	
		return repositorio.obtenerCDRsTarificadasSegun(historial);
	}
	
	public void guardarResultados() {
		repositorio.guardarCDRsTarificadosHistorial(CDRsCargados);
	}
}
