package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Entities.CargadorCDRsDesdeTXT;
import boundaries.LectorDeCDRs;
import boundaries.TarificarBoundary;
import controllers.ControladorCargarCDRs;
import interactors.TarificarInteractor;

class CentralTest {

	@Test
	void testCentralTarificaUnRegistro() {
		Central central = new Central();
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.anadirNumeroAmigo("78888888");
		nuevoCliente.setPlan("Wow");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78888888");
		testCDR.setFecha("2020");
		testCDR.setHora("15");
		testCDR.setTiempoDuracionSegundos(10);
		
		double resultado = central.tarificarCDR(testCDR);
		assertEquals(0.0,resultado, "Esperamos que costo sea 0.0");
	}
	
	@Test
	void testCargarCDRs() {
		Central central = new Central();
		LectorDeCDRs lectorCDRs = new CargadorCDRsDesdeTXT();
		ControladorCargarCDRs controlador = new ControladorCargarCDRs();
		controlador.setRepository(lectorCDRs);
		central.setControladorCargarCDRs(controlador);
		central.cargarCDRsDesdeTexto("datas\\file\\CDR.txt");
		ArrayList<RegistroCDR> listaObtenida = central.getCdrsCargados();
		assertEquals(5,listaObtenida.size(), "Esperamos la misma cantidad de cdr leidas tarificadas: 5");
	}
	
	@Test
	void testTarificarCDRsCargados() {
		Central central = new Central();
		LectorDeCDRs lectorCDRs = new CargadorCDRsDesdeTXT();
		ControladorCargarCDRs controlador = new ControladorCargarCDRs();
		TarificarBoundary ITarificador = new TarificarInteractor();
		controlador.setRepository(lectorCDRs);
		central.setControladorCargarCDRs(controlador);
		central.setTarificador(ITarificador);
		central.cargarCDRsDesdeTexto("datas\\file\\CDR.txt");
		ArrayList<RegistroCDR> listaObtenida = central.tarificarCDRsCargados();
		assertEquals(5,listaObtenida.size(), "Esperamos la misma cantidad de cdr leidas tarificadas: 5");
	}
}
