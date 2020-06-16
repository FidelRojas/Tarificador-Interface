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
	
	public int transformarMes(String Mes) {
		int numeroMes = 0;
		switch (Mes) {
		  case "Enero":
			  numeroMes = 1;
		    break;
		  case "Febrero":
			  numeroMes = 2;
		    break;
		  case "Marzo":
			  numeroMes = 3;
		    break;
		  case "Abril":
			  numeroMes = 4;
		    break;
		  case "Mayo":
			  numeroMes = 5;
		    break;
		  case "Junio":
			  numeroMes = 6;
		    break;
		  case "Julio":
			  numeroMes = 7;
		    break;
		  case "Agosto":
			  numeroMes = 8;
		    break;
		  case "Septiembre":
			  numeroMes = 9;
		    break;
		  case "Octubre":
			  numeroMes = 10;
		    break;
		  case "Noviembre":
			  numeroMes = 11;
		    break;
		  case "Diciembre":
			  numeroMes = 12;
		    break;
		}
	return numeroMes;
	}
	
	public String facturarSegun(String numero, String Mes) {
		String json = ""; 
		
		return json;
	}
}
