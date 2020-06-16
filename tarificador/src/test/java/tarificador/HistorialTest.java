package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HistorialTest {
	
	@Test
	void pruebaDeHistorial() {
		Historial test = new Historial(1,"16/06/2020 16:00"); 
		assertEquals("16/06/2020 16:00", test.getFechaHora(), "Esperamos la fecha y hora de 16/06/2020 16:00");
	}
	
	@Test
	void pruebaDeHistorial2() {
		Historial test = new Historial(2,"25/12/2020 22:00"); 
		assertEquals("25/12/2020 22:00", test.getFechaHora(), "Esperamos la fecha y hora de 25/12/2020 22:00");
	}
	
	@Test
	void pruebaDeHistorial3() {
		Historial test = new Historial(3,"25-12-2020 22:00"); 
		assertEquals("25-12-2020 22:00", test.getFechaHora(), "Esperamos la fecha y hora de 25-12-2020 22:00");
	}
	
	@Test
	void pruebaDeHistorial4() {
		Historial test = new Historial(4,"25-12-2020 22.00"); 
		assertEquals("25-12-2020 22.00", test.getFechaHora(), "Esperamos la fecha y hora en formato 25-12-2020 22.00");
	}
}
