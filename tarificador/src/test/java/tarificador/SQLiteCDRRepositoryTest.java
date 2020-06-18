package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SQLiteCDRRepositoryTest {
	@Test
	void testObtenerCDRs() {
		SQLiteCDRRepository test = new SQLiteCDRRepository("datas\\sql\\dataBaseCentral.db");
		ArrayList<RegistroCDR> listaObtenida = test.getList();
		assertEquals(8,listaObtenida.size(), "Esperamos la misma cantidad de cdr leidas por Base de Datos: 8");
	}
}
