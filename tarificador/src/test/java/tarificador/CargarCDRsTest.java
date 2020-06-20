package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Boundaries.LectorDeCDRs;
import Entities.CargadorCDRsDesdeTXT;
import controllers.ControladorCargarCDRs;

class CargarCDRsTest {

	@Test
	void testCargarCDR() {
		LectorDeCDRs lectorCDRs = new CargadorCDRsDesdeTXT();
		ControladorCargarCDRs controlador = new ControladorCargarCDRs();
		String path = "C:\\Users\\migue\\git\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		controlador.setRepository(lectorCDRs);
		ArrayList<RegistroCDR> registros = controlador.cargarCDR(path);
		
		assertEquals(5, registros.size() , "Comment");
	
	}

}