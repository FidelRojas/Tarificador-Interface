package tarificador;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Central {
	HashMap<String, String> configuraciones;
	private TarificadorBoundary tarificador;
	private ArrayList<RegistroCDR> CDRsCargados;
	private RepositoryBoundary repositorio =null;
	private FacturadorBoundary facturador = null;
	private ListaClientes LC;
	
	public Central() {
		tarificador = new Tarificador();
		LC= ListaClientes.getInstance();
		configuraciones = new HashMap<String, String>();
		configuraciones.put("persistencia", "SQL");
		this.repositorio = new SQLiteCDRRepository();
		facturador = new Facturador();
	}
	public void cargarCDRsDesdeTexto(String path) {
		FileCDRRepository FileRepo = new FileCDRRepository(path);
		CDRsCargados = FileRepo.getList();
		
	}
	
	public void debugMostrar() {
		CDRsCargados = this.repositorio.getList();
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
	
	public String facturarCliente(String numeroBuscado, int mes) {
		ArrayList<RegistroCDR> CDRsDeUnCliente = repositorio.obtenerCDRsTarificadosDe(numeroBuscado);
		double suma = facturador.calcularFactura(numeroBuscado, mes, repositorio);
		JSONObject respuestaJSON = new JSONObject() ;
		String nombre = LC.buscar(numeroBuscado).getNombre();
		if(nombre != null) {
			respuestaJSON.put("nombre", nombre);
		}
		
		respuestaJSON.put("numero", numeroBuscado);
		respuestaJSON.put("suma", suma);
		JSONArray array = new JSONArray();		
		for(RegistroCDR CDR:CDRsDeUnCliente) {
			String fecha = CDR.getFecha();
			String[] partes = fecha.split("/");
			int mesCDR =Integer.parseInt(partes[1]);
			if(mesCDR == mes) {
				array.add(CDR.toJSONString());
			}
		}
		respuestaJSON.put("lista", array);
		return respuestaJSON.toString();
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
