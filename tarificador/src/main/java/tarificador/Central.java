package tarificador;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import boundaries.IPersistencia;
import boundaries.TarificarBoundary;
import controllers.ControladorCargarCDRs;
import controllers.ControladorConfiguracionPersistencia;

public class Central {
	HashMap<String, String> configuraciones;
	private TarificadorBoundary tarificador;
	private ArrayList<RegistroCDR> CDRsCargados;
	private RepositoryBoundary repositorio =null;
	private FacturadorBoundary facturador = null;
	private ListaClientes LC;
	private ControladorCargarCDRs controladorCargarCDRs = null;
	private ControladorConfiguracionPersistencia controladorConfiguracionPersistencia = null;
	
	
	private TarificarBoundary ITarificador = null;
	
	public Central() {
		tarificador = new Tarificador();
		LC= ListaClientes.getInstance();
		configuraciones = new HashMap<String, String>();
		configuraciones.put("persistencia", "SQL");
		this.repositorio = new SQLiteCDRRepository();
		facturador = new Facturador();
	}
	
	public void setControladorCargarCDRs(ControladorCargarCDRs controlador) {
		controladorCargarCDRs = controlador;
	}
	public void setControladorConfiguracionPersistencia(ControladorConfiguracionPersistencia controladorConfiguracionPersistencia) {
		this.controladorConfiguracionPersistencia = controladorConfiguracionPersistencia=null;
	}
	public void setTarificador(TarificarBoundary ITarificador) {
		this.ITarificador = ITarificador;
	}
	
	public void changeRepository(RepositoryBoundary repositorio) {
	}
	
	
	public void cargarCDRsDesdeTexto(String path) {
		/*
		 * FileCDRRepository FileRepo = new FileCDRRepository(path); CDRsCargados =
		 * FileRepo.getList();
		 */
		CDRsCargados = controladorCargarCDRs.cargarCDR(path);
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
	//Caso de Uso : Tarificar CDRs
	public ArrayList<RegistroCDR> tarificarCDRsCargados() { 
//		for(RegistroCDR registro : CDRsCargados) {
//			tarificador.calcularCostoLlamada(registro);
//		}
		ITarificador.tarificarListaDeCDRs(this.CDRsCargados);
		return CDRsCargados;
	}
	
	public ArrayList<RegistroCDR> getCdrsCargados() {
		return CDRsCargados;
	}
	
	public void borrarCDRsCargados() {
		this.CDRsCargados.clear();
	}
	//Caso de Uso: Facturar
	public String facturarCliente(String numeroBuscado, int mes) {
		ArrayList<RegistroCDR> CDRsDeUnCliente = repositorio.obtenerCDRsTarificadosDe(numeroBuscado);
		double suma = facturador.calcularFactura(numeroBuscado, mes, repositorio);
		JSONObject respuestaJSON = new JSONObject() ;
		//String nombre = LC.buscar(numeroBuscado).getNombre();
		//if(nombre != null) {
		//	respuestaJSON.put("nombre", nombre);
		//}
		
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
	//Caso de Uso: Cambiar configuracion de persistencia
	public void cambiarConfiguracion(String key, String value) {
		controladorConfiguracionPersistencia.cambiarConfiguracionDePersistencia(value);
	}
	//Caso de Uso: Recuperar Historial
	public ArrayList<Historial> getHistorial(){
		return repositorio.obtenerHistorialDeTarificaciones();
	}
	
	public ArrayList<RegistroCDR> obtenerCDRsDeUnHistorial(Historial historial){
	
		return repositorio.obtenerCDRsTarificadasSegun(historial);
	}
	//Caso de Uso: Guardar
	public void guardarResultados() {
		repositorio.guardarCDRsTarificadosHistorial(CDRsCargados);
	}
	

}
