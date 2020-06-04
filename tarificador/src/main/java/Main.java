import static spark.Spark.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import spark.Request;
import tarificador.Cliente;
import tarificador.ListaClientes;
import tarificador.RegistroCDR;
import tarificador.SQLiteCDRRepository;
import tarificador.Tarificador;

public class Main {
	public static void main(String[] args) {
		get("/", (request, response) -> home());
		get("/tarificar", (request, response) -> tarificar());

		File uploadDir = new File("upload");
		uploadDir.mkdir(); // create the upload directory if it doesn't exist

		staticFiles.externalLocation("upload");

		get("/tarificartxt", (req, res) -> "<form method='post' enctype='multipart/form-data'>" // note the enctype
				+ "    <input type='file' name='uploaded_file' accept='.png'>" // make sure to call getPart using the
																				// same "name" in the post
				+ "    <button>Upload picture</button>" + "</form>");
		
		post("/tarificartxt", (req, res) -> {

			Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

			try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same
																							// "name" as input field in
																							// form
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}

			logInfo(req, tempFile);
			return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";

		});

	}

	private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
		System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '"
				+ tempFile.toAbsolutePath() + "'");
	}

	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static ArrayList<RegistroCDR> cargarDatos() {
		// TODO Auto-generated method stub
		String url = "C:\\\\Users\\\\migue\\\\Documents\\\\Tarificador-Interface\\\\tarificador\\\\datas\\\\sql\\\\dataBaseCentral.db";
		SQLiteCDRRepository c = new SQLiteCDRRepository(url);
		ArrayList<RegistroCDR> listCDR = c.getList();
		double costo = 0.0;

//			Tarificador tarificador = new Tarificador();
//			for (RegistroCDR r:listCDR) {
//				Cliente nuevoCliente = new Cliente(); 
//				nuevoCliente.setNumero("77777777");
//				nuevoCliente.anadirNumeroAmigo("78888888");
//				nuevoCliente.setPlan("Wow");
//				ListaClientes LC= ListaClientes.getInstance();
//				LC.añadir(nuevoCliente);
//				tarificador.setRegistro(r);
//				costo= tarificador.calcularCostoLlamada();
//				r.setCosto(costo);
//				//System.out.println("Origen: " + registro.getTelefonoOrigen() + " Destino: " + registro.getTelefonoDestino() + " Fecha: " + registro.getFecha() + " Hora: " + registro.getHora() + " Duracion: " + registro.getTiempoDuracionSegundos() + " Costo: " + registro.getCosto());
//			}
		return listCDR;
	}

	private static String home() {
		return "<html>" + "<body>" + "<a href=\"/tarificar\"><button>Tarificar</button></a>"
				+ "<form method='post' enctype='multipart/form-data' >" // note the enctype
				+ "    <input type='file' name='uploaded_file' accept='.txt'>" // make sure to call getPart using the
																				// same "name" in the post
				+ "    <button>Upload file</button>" + "</form>" + "</body>" + "</html>";

	}

	private static String tarificar() {
		ArrayList<RegistroCDR> lista = cargarDatos();
		return html(lista);
	}

	private static String html(ArrayList<RegistroCDR> lista) {

		String head = "<head>" + "<style>" + "table {" + "  border-collapse: collapse;" + "  width: 100%;" + "}"
				+ "th, td {" + "  text-align: left;" + "  padding: 8px;" + "}"
				+ "tr:nth-child(even){background-color: #f2f2f2}" + "th {" + "  background-color: #4CAF50;"
				+ "  color: white;" + "}" + "</style>" + "</head>" + "<body>" + "<h2>Tarificacion de CDRs</h2>"
				+ "<table>" + "  <tr>" + "    <th>Origen</th>" + "    <th>Destino</th>" + "    <th>Fecha</th>"
				+ "    <th>Hora</th>" + "    <th>Duración</th>" + "    <th>Costo</th>" + "  </tr>";
		String body = getCdrsTable(lista);
		String end = "</table>" + "</body>";

		return head + body + end;
<<<<<<< Updated upstream
}
	private static String getCdrsTable() {
		ArrayList<RegistroCDR> cdrs = new ArrayList<RegistroCDR>();
		RegistroCDR test=new RegistroCDR();
		test.setTelefonoOrigen("77777777");
		test.setTelefonoDestino("76666666");
		test.setFecha("07/21/2019");
		test.setHora("2200");
		test.setTiempoDuracionSegundos(10);
		test.setCosto(11.5);
		
		RegistroCDR test2=new RegistroCDR();
		test.setTelefonoOrigen("70725841");
		test.setTelefonoDestino("77777778");
		test.setFecha("12/12/2014");
		test.setHora("1500");
		test.setTiempoDuracionSegundos(10);
		test.setCosto(11.5);
		
		RegistroCDR test3=new RegistroCDR();
		test.setTelefonoOrigen("75656474");
		test.setTelefonoDestino("76666666");
		test.setFecha("07/21/2018");
		test.setHora("2200");
		test.setTiempoDuracionSegundos(10);
		test.setCosto(11.5);

		cdrs.add(test);
		cdrs.add(test2);
		cdrs.add(test3);
		String res="";
		for (RegistroCDR c : cdrs) {
            res=res+getRegistoCdrTable(c);
        }
=======
	}

	private static String getCdrsTable(ArrayList<RegistroCDR> lista) {

		String res = "";
		for (RegistroCDR c : lista) {
			res = res + getRegistoCdrTable(c);
		}
>>>>>>> Stashed changes
		return res;
	}

	private static String getRegistoCdrTable(RegistroCDR r) {
<<<<<<< Updated upstream
		String res = " <tr>" +
				"    <td>"+ r.getTelefonoOrigen() +"</td>" +
				"    <td>"+ r.getTelefonoDestino() +"</td>" +
				"    <td>"+ r.getFecha() +"</td>" +
				"    <td>"+ r.getHora() +"</td>" +
				"    <td>"+ r.getTiempoDuracionSegundos() +"</td>" +
				"    <td>"+ r.getCosto() +"</td>" +
				"</tr>";
=======
		String res = " <tr>" + "    <td>" + r.getTelefonoOrigen() + "</td>" + "    <td>" + r.getTelefonoDestino()
				+ "</td>" + "    <td>" + r.getFecha() + "</td>" + "    <td>" + r.getHora() + "</td>" + "    <td>"
				+ r.getTiempoDuracionSegundos() + "</td>" + "    <td>" + r.getCosto() + "</td>" + "</tr>";

>>>>>>> Stashed changes
		return res;
	}
}
