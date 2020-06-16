package interfaces;
import static spark.Spark.*;

import tarificador.Central;

public class InterfaceFacturadora {
	static Central central ;
	
	public static void run() {		
		port(1010);
		get("/facturar/:numero/:mes", (request, response) -> { 
			String numero = request.params(":numero");
			String mes = request.params(":mes");
			String jsonString = obtenerFacturaPor(numero, mes);
			return "";
		});
	}
	
	private static String obtenerFacturaPor(String numero, String mes) {
		String strJson = central.facturarSegun(numero, mes);
		return strJson;
	}
}
