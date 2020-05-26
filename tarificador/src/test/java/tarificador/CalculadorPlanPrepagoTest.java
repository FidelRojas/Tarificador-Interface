package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadorPlanPrepagoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCalcularCostoNormal() {
		CalculadorDeCosto calcPrepago = new CalculadorPlanPrepago();
		RegistroCDR cdr = new RegistroCDR();
		cdr.setTelefonoOrigen("70724245");
		cdr.setTelefonoDestino("72222300");
		cdr.setFecha("12/12/12");
		cdr.setHora("19:14");
		cdr.setTiempoDuracionSegundos(155);
		assertEquals(Math.round(155*1.45/60 * 100.0) /100.0, calcPrepago.calcularCosto(cdr), "Horario Normal");
	}
	@Test
	void testCalcularCostoReducido() {
		CalculadorDeCosto calcPrepago = new CalculadorPlanPrepago();
		RegistroCDR cdr = new RegistroCDR();
		cdr.setTelefonoOrigen("70724245");
		cdr.setTelefonoDestino("72222300");
		cdr.setFecha("12/12/12");
		cdr.setHora("22:10");
		cdr.setTiempoDuracionSegundos(155);
		assertEquals(Math.round(155*0.95/60 * 100.0) / 100.0, calcPrepago.calcularCosto(cdr), "Horario Reducido");
	}
	@Test
	void testCalcularCostoSuperReducido() {
		CalculadorDeCosto calcPrepago = new CalculadorPlanPrepago();
		RegistroCDR cdr = new RegistroCDR();
		cdr.setTelefonoOrigen("70724245");
		cdr.setTelefonoDestino("72222300");
		cdr.setFecha("12/12/12");
		cdr.setHora("2:47");
		cdr.setTiempoDuracionSegundos(155);
		assertEquals(Math.round(155*0.7/60 * 100.0) / 100.0, calcPrepago.calcularCosto(cdr), "Horario Super Reducido");
	}

}
