package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TarificacionTest {
	
	@Test
	void testWoWesAmigo() {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.anadirNumeroAmigo("78888888");
		nuevoCliente.setPlan("Wow");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78888888");
		testCDR.setFecha("10/05/2020");
		testCDR.setHora("15:00");
		testCDR.setTiempoDuracionSegundos(10);
		
		Tarificador tarificador = new Tarificador();
		double resultadoObtenido  = tarificador.calcularCostoLlamada(testCDR);
		assertEquals(0.0,resultadoObtenido, "Esperamos que costo sea 0.0");
	}
	
	@Test
	void testWoWNoEsAmigo() {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.anadirNumeroAmigo("78888888");
		nuevoCliente.setPlan("Wow");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78844888");
		testCDR.setFecha("2020");
		testCDR.setHora("15");
		testCDR.setTiempoDuracionSegundos(90);
		
		Tarificador tarificador = new Tarificador();
		double resultadoObtenido  = tarificador.calcularCostoLlamada(testCDR);
		assertEquals(1.5,resultadoObtenido, "Esperamos que costo sea 1.5");
	}
	
	@Test
	void testPrepago(){
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.setPlan("PrePago");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78844888");
		testCDR.setFecha("2020");
		testCDR.setHora("19");
		testCDR.setTiempoDuracionSegundos(155);
		
		Tarificador tarificador = new Tarificador();
		double resultadoObtenido  = tarificador.calcularCostoLlamada(testCDR);
		assertEquals(2.5833333333333335,resultadoObtenido, "Horario Normal, esperamos 2.58");
	}
	
	@Test
	void testPostpago(){
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.setPlan("PostPago");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78844888");
		testCDR.setFecha("2020");
		testCDR.setHora("19");
		testCDR.setTiempoDuracionSegundos(90);
		
		Tarificador tarificador = new Tarificador();
		double resultadoObtenido  = tarificador.calcularCostoLlamada(testCDR);
		assertEquals(1.5,resultadoObtenido, "Postpago, esperamos 1.5");
	}
}
