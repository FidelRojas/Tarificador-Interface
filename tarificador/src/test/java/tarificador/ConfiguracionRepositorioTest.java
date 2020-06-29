package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Entities.CargadorCDRsDesdeTXT;
import boundaries.IPersistencia;
import controllers.ControladorConfiguracionPersistencia;
import interactors.Persistencia;
import interfaces.Interface;

class ConfiguracionRepositorioTest {

	@Test
	void testConfigurarSQL() {
		IPersistencia persistencia = new Persistencia();
		ControladorConfiguracionPersistencia controlador = new ControladorConfiguracionPersistencia();
		//String path = "C:\\Users\\migue\\git\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		controlador.setRepository(persistencia);
		Central central =new Central();
		central.setControladorConfiguracionPersistencia(controlador);
		Interface inter =new Interface();
		inter.setCentral(central);
		inter.configurarHtml("TXT");
		assertEquals("TXT", persistencia.configurarPersistencia("TXT") , "La persistencia tiene que ser SQL");
	
	}
}
