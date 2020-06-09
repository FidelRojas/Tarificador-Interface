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

import tarificador.RegistroCDR;

public class Interface {
	private static String persistencia = "SQL";
	private static String pathTXT = "";
	private static List<RegistroCDR> cdrsSinTarificar = new ArrayList<RegistroCDR>();
	private static List<RegistroCDR> cdrsTarificados = new ArrayList<RegistroCDR>();
	public static void run() {
		get("/", (request, response) -> homeHtml());
		get("/tarificar", (request, response) -> tarificarHtml());
		get("/configurar", (request, response) -> {
			return configurarHtml(request.queryParams("percistencia"));
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
		//aqui va codigo de tarificar
		cdrsSinTarificar= new ArrayList<RegistroCDR>();
		cdrsTarificados= new ArrayList<RegistroCDR>();
		return readHtml("home");
	}
	private static String tarificarBound() {
		//aqui va codigo de tarificar
		cdrsTarificados=getCdrs();
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
			pathTXT=path;
			//aca llamar al boundary,
			//cdrsSinTarificar=funcion(path);
			cdrsSinTarificar=getCdrs();
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

	private static String configurarHtml(String per) {
		if(per != null) {
			persistencia=per;
			//aca llamar al boundary, per puede ser SQL o TXT
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

	private static String html() {

		String head = "<h2>Tarificacion de CDRs</h2>" + "<table>" + "  <tr>" + "    <th>Origen</th>"
				+ "    <th>Destino</th>" + "    <th>Fecha</th>" + "    <th>Hora</th>" + "    <th>Duración</th>"
				+ "    <th>Costo</th>" + "  </tr>";
		String body = getCdrsTable();
		String end = "</table>" + "</div>" + "</body>";

		return head + body + end;
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

	private static String getCdrsTable() {
		ArrayList<RegistroCDR> cdrs = new ArrayList<RegistroCDR>();
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
		String res = "";
		for (RegistroCDR c : cdrs) {
			res = res + getRegistoCdrTable(c);
		}
		return res;
	}

	private static String getRegistoCdrTable(RegistroCDR r) {
		String res = " <tr>" + "    <td>" + r.getTelefonoOrigen() + "</td>" + "    <td>" + r.getTelefonoDestino()
				+ "</td>" + "    <td>" + r.getFecha() + "</td>" + "    <td>" + r.getHora() + "</td>" + "    <td>"
				+ r.getTiempoDuracionSegundos() + "</td>" + "    <td>" + r.getCosto() + "</td>" + "</tr>";

		return res;
	}
}
