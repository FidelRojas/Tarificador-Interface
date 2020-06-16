package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TarificadorTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
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
		testCDR.setFecha("2020");
		testCDR.setHora("15");
		testCDR.setTiempoDuracionSegundos(10);
		double resultado = 0.0;
		Central central = new Central();
		resultado = central.tarificarCDR(testCDR);
		
		Tarificador tarificador = new Tarificador();
		tarificador.calcularCostoLlamada(testCDR);
		assertEquals(0,resultado, "Esperamos que costo sea 0");
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
		
		tarificador.calcularCostoLlamada(testCDR);
		assertEquals(1.5,tarificador.calcularCostoLlamada(testCDR), "Esperamos que costo sea 1.5");
	}
	
	@Test
	void testPrepago(){
		Cliente nuevoCliente = new Cliente(); 
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
		tarificador.calcularCostoLlamada(testCDR);
		assertEquals(2.5833333333333335,tarificador.calcularCostoLlamada(testCDR), "Horario Normal, esperamos 2.58");

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
		
		tarificador.calcularCostoLlamada(testCDR);
		
		assertEquals(1.5,tarificador.calcularCostoLlamada(testCDR), "Postpago, esperamos 1.5");


	}
}
