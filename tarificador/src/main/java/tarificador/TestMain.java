package tarificador;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestMain {
	public static void main(String[] args) {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.setPlan("PrePago");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		Cliente otroCliente = new Cliente(); 
		otroCliente.setNumero("70442222");
		otroCliente.setPlan("PostPago");
		LC.añadir(otroCliente);
		
		Cliente otroCliente1 = new Cliente(); 
		otroCliente1.setNumero("70304423");
		otroCliente1.setPlan("PrePago");
		LC.añadir(otroCliente1);
		
		Cliente otroCliente2 = new Cliente(); 
		otroCliente2.setNumero("60303030");
		otroCliente2.setPlan("PrePago");
		LC.añadir(otroCliente2);
		
		Cliente otroCliente3 = new Cliente(); 
		otroCliente3.setNumero("72222300");
		otroCliente3.setPlan("PrePago");
		LC.añadir(otroCliente3);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78844888");
		testCDR.setFecha("2020");
		testCDR.setHora("19:00");
		testCDR.setTiempoDuracionSegundos(155);
		
		Tarificador tarificador = new Tarificador();
		tarificador.calcularCostoLlamada(testCDR);
		
		Central central = new Central();
		double resultado = central.tarificarCDR(testCDR);
		System.out.println(resultado);;
		
		JSONParser parser = new JSONParser();
		
		central.debugMostrar();
		String respuestaJSON = central.facturarCliente("70442222", 5);
		System.out.println(respuestaJSON);
	    JSONParser jsonParser = new JSONParser();
	    try {
	    	JSONObject json = (JSONObject) parser.parse(respuestaJSON);
			System.out.println(json.get("lista"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
