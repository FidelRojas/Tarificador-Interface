package interfaces;

import static spark.Spark.*;

import spark.Filter;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.IOException;

import tarificador.Central;
import tarificador.Historial;
import tarificador.RegistroCDR;

public class Interface {
	static Central central ;
	private static String persistencia = "SQL";
	private static List<RegistroCDR> cdrsSinTarificar = new ArrayList<RegistroCDR>();
	private static List<RegistroCDR> cdrsTarificados = new ArrayList<RegistroCDR>();
	private static List<RegistroCDR> cdrsHistorial = new ArrayList<RegistroCDR>();
	private static List<Historial> historiales = new ArrayList<Historial>();
	
	public static void run(Central _central) {
		central=_central;
		get("/", (request, response) -> homeHtml());
		get("/tarificar", (request, response) -> tarificarHtml());
		get("/historial", (request, response) -> historialHtml());
		get("/configurar", (request, response) -> {
			return configurarHtml(request.queryParams("percistencia"));
		});
		get("/historial/:id", (request, response) -> {
			String res =request.params(":id");
		    return historialDetalleHtml(Integer.parseInt(res));
		});
		
		get("/cargarTXTpaht", (request, response) -> {
			String path="";
			path=request.queryParams("ruta");
			String res = cargar_CDRSHtml(path);
			response.redirect("/tarificar");
			return res;
		});
		get("/cargarTXT", (request, response) -> cargar_CDRSHtml(request.queryParams("ruta")));

		post("/tarificar", (request, response) -> {
			tarificarBound();
			response.redirect("/tarificar");
			return "";
		});
		post("/descarcarCarga", (request, response) -> {
			String res= descartarCdrsSinTarificar();
			response.redirect("/tarificar");
			return res;
		});
		post("/guardar", (request, response) -> {
			guardartarificacionBound();
			response.redirect("/tarificar");
			return "";
		});
		post("/descarcarTarificacion", (request, response) -> {
			String res= descartarcdrsTarificados();
			response.redirect("/tarificar");
			return res;
		});
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
	private static String guardartarificacionBound() {
		central.guardarResultados();
		cdrsSinTarificar= new ArrayList<RegistroCDR>();
		cdrsTarificados= new ArrayList<RegistroCDR>();
		return readHtml("home");
	}
	private static String tarificarBound() {
		
		cdrsTarificados=central.tarificarCDRsCargados();
		return readHtml("home");
	}
	private static String descartarCdrsSinTarificar() {
		cdrsSinTarificar= new ArrayList<RegistroCDR>();
		central.borrarCDRsCargados();
		return "";
	}
	private static String descartarcdrsTarificados() {
		cdrsTarificados= new ArrayList<RegistroCDR>();
		central.borrarCDRsCargados();
		return "";
	}
	private static String homeHtml() {
		return readHtml("home");
	}

	private static String cargar_CDRSHtml(String path) {
		if(path != null) {
			central.cargarCDRsDesdeTexto(path);
			cdrsSinTarificar=central.getCdrsCargados();
		}
		String html = readHtml("cargar_CDRS");
		return html;
	}

	private static String tarificarHtml() {
		Map<String, Object> model = new HashMap<>();
		model.put("cdrs",cdrsSinTarificar);
		model.put("cdrsTarificados", cdrsTarificados);
		return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/cdrs/Tarificacion.vm"));
	}

	private static String historialHtml() {
		Map<String, Object> model = new HashMap<>();
		historiales=central.getHistorial();
		model.put("historial",historiales);
		return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/cdrs/Historial.vm"));
	}
	
	private static String historialDetalleHtml(int id) {
		
		for (Historial historial : historiales) {
			if(historial.getId() == id) {
				cdrsHistorial=central.obtenerCDRsDeUnHistorial(historial);
			}
		}
		Map<String, Object> model = new HashMap<>();
		model.put("cdrs",cdrsHistorial);
		return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/cdrs/Historial-detalle.vm"));
	}

	private static String configurarHtml(String per) {
		if(per != null) {
			persistencia=per;
			central.cambiarConfiguracion("persistencia", per);
		}
		Map<String, Object> model = new HashMap<>();
		model.put("persistencia", persistencia);
		return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/cdrs/configurar.vm"));

	}

	public static String readHtml(String path) {

		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader("src\\main\\resources\\inputs\\" + path + ".html"));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		return contentBuilder.toString();
	}
	
}
