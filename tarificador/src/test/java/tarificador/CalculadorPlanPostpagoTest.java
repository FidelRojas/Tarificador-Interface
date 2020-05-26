package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadorPlanPostpagoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCalcularCostoNormal() {
		ListaClientes clientes= ListaClientes.getInstance();
		Cliente cliente=new Cliente();
		cliente.setNumero("72202143");
		cliente.setNombre("Fidel");
		cliente.anadirNumeroAmigo("77777777");
		clientes.añadir(cliente);
		CalculadorDeCosto calcPrepago = new CalculadorPlanPostpago();
		RegistroCDR cdr = new RegistroCDR();
		cdr.setTelefonoOrigen("70724245");
		cdr.setTelefonoDestino("72222300");
		cdr.setFecha("12/12/12");
		cdr.setHora("19:14");
		
		cdr.setTiempoDuracionSegundos(155);
		assertEquals(Math.round(155.0/60 * 100.0) / 100.0,calcPrepago.calcularCosto(cdr), "Horario Normal");
	}

	@Test
	void testCalcularCostoAmigo() {
		ListaClientes clientes= ListaClientes.getInstance();
		Cliente cliente=new Cliente();
		cliente.setNumero("72202143");
		cliente.setNombre("Fidel");
		cliente.anadirNumeroAmigo("77777777");
		clientes.añadir(cliente);
		CalculadorDeCosto calcPrepago = new CalculadorPlanPostpago();
		RegistroCDR cdr = new RegistroCDR();
		cdr.setTelefonoOrigen("72202143");
		cdr.setTelefonoDestino("77777777");
		cdr.setFecha("12/12/12");
		cdr.setHora("19:14");
		
		cdr.setTiempoDuracionSegundos(155);
		assertEquals(0,calcPrepago.calcularCosto(cdr), "Horario Normal");
	}
	

}
