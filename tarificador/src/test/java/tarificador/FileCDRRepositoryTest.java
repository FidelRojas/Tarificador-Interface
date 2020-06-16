package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FileCDRRepositoryTest {

	@Test
	void testDadaUnaFechaEsteEnFormatoFileTxt() {
		String fecha = "16/06/2020 08:00";
		FileCDRRepository test = new FileCDRRepository();
		assertEquals("16-06-2020,08.00.txt",test.convertirFechaEnFormatoTxt(fecha), "Esperamos que file.txt sea 16-06-2020,08.00.txt");
	}
	
	@Test
	void testConvertirDuracionEnFormatoCorrecto() {
		String tiempo = "01:00";
		FileCDRRepository test = new FileCDRRepository();
		assertEquals(60,test.convertirDuracionEnFormatoCorrecto(tiempo), "Esperamos que 01:00 sea 60 segundos");
	}
	
	@Test
	void testConvertirNombreEnFormatoFecha() {
		String fechaUrl = "16-06-2020,08.00.txt";
		FileCDRRepository test = new FileCDRRepository();
		assertEquals("16/06/2020 08:00",test.convertirNombreEnFormatoFecha(fechaUrl), "Esperamos que 16-06-2020,08.00.txt sea 16/06/2020 08:00");
	}
	
	@Test
	void testObtenerCabeceraCDR() {
		FileCDRRepository test = new FileCDRRepository();
		assertEquals("telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo",test.obtenerCabeceraCDR(), "Esperamos que retorne telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo");
	}
	
	@Test
	void testTransformarCDRaString() {
		RegistroCDR testCdr = new RegistroCDR("68442222","68441111","10/02/2020", "15:00", 15, 1.5);
		FileCDRRepository test = new FileCDRRepository();
		assertEquals("68442222, 68441111, 10/02/2020, 15:00, 15, 1.5",test.transformarCDRaString(testCdr), "Esperamos que retorne 68442222, 68441111, 10/02/2020, 15:00, 15, 1.5");
	}
	
	@Test
	void testObtenerCDRs() {
		FileCDRRepository test = new FileCDRRepository("datas\\file\\CDR.txt");
		ArrayList<RegistroCDR> listaObtenida = test.getList();
		assertEquals(5,listaObtenida.size(), "Esperamos la misma cantidad de cdr leidas: 5");
	}
}
