package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegistroCDRTest {

	@Test
	void SetYGetTelefonoOrigen() {
		RegistroCDR test = new RegistroCDR(); 
		test.setTelefonoOrigen("77777777");
		assertEquals("77777777", test.getTelefonoOrigen(), "Esperamos 77777777");
	}
	@Test
	void SetYGetTelefonoDestino() {
		RegistroCDR test = new RegistroCDR(); 
		test.setTelefonoDestino("77777777");
		assertEquals("77777777", test.getTelefonoDestino(), "Esperamos 77777777");
	}
	@Test
	void SetYGetFecha() {
		RegistroCDR test = new RegistroCDR(); 
		test.setFecha("22022020");
		assertEquals("22022020", test.getFecha(), "Esperamos 22022020");
	}
	@Test
	void SetYGetHora() {
		RegistroCDR test = new RegistroCDR(); 
		test.setHora("22022020");
		assertEquals("22022020", test.getHora(), "Esperamos 22022020");
	}
	@Test
	void SetYGetTiempoDuracion() {
		RegistroCDR test = new RegistroCDR(); 
		test.setTiempoDuracionSegundos(10);
		assertEquals(10, test.getTiempoDuracionSegundos(), "Esperamos 10");
	}
	@Test
	void SetYGetCosto() {
		RegistroCDR test = new RegistroCDR(); 
		test.setCosto(11.5);
		assertEquals(11.5, test.getCosto(), "Esperamos 11.5");
	}
	
	@Test
	void retornaCadenaDelCDR() {
		RegistroCDR test = new RegistroCDR(); 
		test.setTelefonoOrigen("a");
		test.setTelefonoDestino("b");
		test.setFecha("2020");
		test.setHora("15");
		test.setTiempoDuracionSegundos(10);
		test.setCosto(11.5);
		String cadenaRespuesta="a b 2020 15 10 11.5"; 
		assertEquals(cadenaRespuesta, test.retornarCadenaCDR(" "), "Esperamos a b 2020 15 10 11.5");
	}
}
