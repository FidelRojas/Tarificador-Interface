package tarificador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalculadorPlanWowTest {

	@Test
	void CalcularParaUnAmigo() {
		ListaClientes clientes= ListaClientes.getInstance();
		Cliente cliente=new Cliente();
		cliente.setNumero("72202143");
		cliente.setNombre("Fidel");
		cliente.anadirNumeroAmigo("77777777");
		clientes.añadir(cliente);
		RegistroCDR registro=new RegistroCDR();
		registro.setTiempoDuracionSegundos(60);
		registro.setTelefonoDestino("77777777");
		registro.setTelefonoOrigen("72202143");
		CalculadorDeCosto calculadorDeCosto =new CalculadorPlanWow();
		double costo= calculadorDeCosto.calcularCosto(registro);
		assertEquals(costo, 0, "Esperamos 0");
	}
	@Test
	void CalcularParaUnNoAmigo() {
		ListaClientes clientes= ListaClientes.getInstance();
		Cliente cliente=new Cliente();
		cliente.setNumero("72202143");
		cliente.setNombre("Fidel");
		cliente.anadirNumeroAmigo("77777777");
		clientes.añadir(cliente);
		RegistroCDR registro=new RegistroCDR();
		registro.setTiempoDuracionSegundos(90);
		registro.setTelefonoDestino("78888888");
		registro.setTelefonoOrigen("72202143");
		CalculadorDeCosto calculadorDeCosto =new CalculadorPlanWow();
		double costo= calculadorDeCosto.calcularCosto(registro);
		assertEquals(costo, 1.5, "Esperamos 1");
	}
}

