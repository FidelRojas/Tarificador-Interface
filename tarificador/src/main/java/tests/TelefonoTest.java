import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelefonoTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void testGetNumero() {
		Telefono test = new Telefono();
		test.setNumero("911");
		test.setPlan("WOW");
		test.setTitular("Alguien");
		assertEquals("911", test.getNumero(), "Esperamos 911");
	}


	@Test
	void testGetTitular() {
		Telefono test = new Telefono();
		test.setNumero("911");
		test.setPlan("WOW");
		test.setTitular("Alguien");
		assertEquals("WOW", test.getPlan(), "Esperamos 'WOW'");
	}

	@Test
	void testGetPlan() {
		Telefono test = new Telefono();
		test.setNumero("911");
		test.setPlan("WOW");
		test.setTitular("Alguien");
		assertEquals("Alguien", test.getTitular(), "Esperamos 'Alguien'");
	}

}
