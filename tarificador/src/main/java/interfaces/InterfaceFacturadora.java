package interfaces;
import static spark.Spark.*;
import spark.Filter;

import tarificador.Central;

public class InterfaceFacturadora {
	static Central central ;
	
	public static void run( Central _central) {
		central=_central;
		port(7654);
		after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
		get("/", (request, response) -> "ASDASDS");
		get("/facturar/:numero/:mes", (request, response) -> { 
			String numero = request.params(":numero");
			String mes = request.params(":mes");
			String jsonString = obtenerFacturaPor(numero, mes);
			return jsonString;
		});
	}
	
	private static String obtenerFacturaPor(String numero, String mes) {
		String strJson = central.facturarCliente(numero, Integer.parseInt(mes));
		return strJson;
	}
}
