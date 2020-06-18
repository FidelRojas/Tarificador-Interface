package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class FacturadorTest {

	@Test
	void testCalcularFacturaSinResultados() {
		Central centralPrueba = new Central();
		String respuesta = centralPrueba.facturarCliente("72222222", 3);
		JSONObject obj = null;
		try {
			obj = (JSONObject) new JSONParser().parse(respuesta);
		} catch (ParseException e) {
		}
		double expectedSum = 0;
		
		String expectedJSON = "{\"suma\":0.0,\"lista\":[],\"numero\":\"72222222\"}";
		assertEquals(expectedSum,obj.get("suma"), "Esperamos un Json");

	}
	
	@Test
	void testCalcularFacturaUnCliente() {
		Central centralPrueba = new Central();
		String respuesta = centralPrueba.facturarCliente("68442222", 5);
		JSONObject obj = null;
		try {
			obj = (JSONObject) new JSONParser().parse(respuesta);
		} catch (ParseException e) {
		}
		double expectedSum = 1;
		
		String expectedJSON = "{\"suma\":0.0,\"lista\":[],\"numero\":\"68442222\"}";
		assertEquals(expectedSum,obj.get("suma"), "Esperamos un Json");

	}

}
