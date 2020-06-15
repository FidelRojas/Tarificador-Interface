package interfaces;

import static spark.Spark.*;
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

	private static List<Historial> historial = new ArrayList<Historial>();

	public static void run(Central _central) {
		central=_central;
		get("/", (request, response) -> homeHtml());
		get("/tarificar", (request, response) -> tarificarHtml());
		get("/historial", (request, response) -> historialHtml());
		get("/configurar", (request, response) -> {
			return configurarHtml(request.queryParams("percistencia"));
		});
		get("/historial/:id", (request, response) -> {
		    return historialDetalleHtml(request.params(":name"));
		});
		
		get("/cargarTXTpaht", (request, response) -> {
			String res = cargar_CDRSHtml(request.queryParams("ruta"));
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
		return "";
	}
	private static String descartarcdrsTarificados() {
		cdrsTarificados= new ArrayList<RegistroCDR>();
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
	private static void setHistorial() {
		Historial h =new Historial();
		h.setFechaHora("11-06-2020 10:01:01");
		h.setId(1);
		historial.add(h);
		h.setFechaHora("12-06-2020 05:01:01");
		h.setId(2);
		historial.add(h);
		h.setFechaHora("12-06-2020 22:01:01");
		h.setId(3);
		historial.add(h);
	}
	private static String historialHtml() {
		setHistorial();
		Map<String, Object> model = new HashMap<>();

		model.put("Historial",historial);
		return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/cdrs/Historial.vm"));
	}
	
	private static String historialDetalleHtml(String id) {
		//getCdrsPorIdHistorial(id);
		Map<String, Object> model = new HashMap<>();
		model.put("cdrs",cdrsSinTarificar);
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

	private static List<RegistroCDR> getCdrs() {
		List<RegistroCDR> cdrs = new ArrayList<RegistroCDR>();
		RegistroCDR test = new RegistroCDR();
		test.setTelefonoOrigen("77777777");
		test.setTelefonoDestino("76666666");
		test.setFecha("22022020");
		test.setHora("2200");
		test.setTiempoDuracionSegundos(10);
		test.setCosto(11.5);

		cdrs.add(test);
		cdrs.add(test);
		cdrs.add(test);
		return cdrs;
	}

	
}
