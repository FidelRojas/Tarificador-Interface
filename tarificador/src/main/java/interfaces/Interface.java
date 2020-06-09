package interfaces;

import static spark.Spark.get;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import spark.utils.IOUtils;
import tarificador.RegistroCDR;

public class Interface {

	public static void run() {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "casspatp");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();

		// 2
		List<RegistroCDR> cdrs = new ArrayList<RegistroCDR>();
		cdrs = getCdrs();

		// 3
		VelocityContext context = new VelocityContext();
		context.put("title", "Lista de Tarificacion");
		context.put("cdrs", cdrs);

		// 4
		//Template template = engine.getTemplate();
		FileOutputStream fos;
		String file = "src/main/resources/templates/listaCdrs.vm";
		
		Template template = engine.getTemplate(file);
		// 5
		try {
			FileWriter fileWriter = new FileWriter(new File("src/main/resources/outputs/listaCdrs.html"));
			//template.merge(context, fileWriter);
			fileWriter.flush();
			fileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void run2() {
		get("/", (request, response) -> homeHtml());
		get("/tarificar", (request, response) -> tarificar());
		get("/configurar", (request, response) -> configurarHtml());

		get("/cargarTXT", (request, response) -> cargar_CDRSHtml());
		
	}

	private static String homeHtml() {
		return readHtml("home");
	}

	private static String cargar_CDRSHtml() {

		String html = readHtml("cargar_CDRS");
		return html;
	}
	private static String tarificar() {
		String htmlNavbar = readHtml("tarificacion");
		return htmlNavbar + html();
	}

	private static String configurarHtml() {
		String html = readHtml("configuracion");
		return html;

	}

	private static String readHtml(String path) {
		
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader("G:\\FidelRojas\\UCB\\arqui\\Tarificador-Interface\\tarificador\\src\\main\\resources\\inputs\\"+path+".html"));
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

		String head = "<h2>Tarificacion de CDRs</h2>"
				+ "<table>" + "  <tr>" + "    <th>Origen</th>" + "    <th>Destino</th>" + "    <th>Fecha</th>"
				+ "    <th>Hora</th>" + "    <th>Duración</th>" + "    <th>Costo</th>" + "  </tr>";
		String body = getCdrsTable();
		String end = "</table>" +"</div>"+ "</body>";

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
