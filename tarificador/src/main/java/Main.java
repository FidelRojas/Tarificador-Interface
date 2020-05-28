import static spark.Spark.*;

import java.awt.List;
import java.util.ArrayList;

import tarificador.RegistroCDR;

public class Main {
	public static void main(String[] args) {
		get("/", (request, response) -> home());
		get("/tarificar", (request, response) -> tarificar());
		
	}
	
	private static String home() {
		return "<html>"
				+ "<body>"
				+ "<a href=\"/tarificar\"><button>Tarificar</button></a>"
				+ "</body>"
				+ "</html>";
		
	}

	private static String tarificar() {
		
		return html();
	}

	
	
	
	private static String html() {

		String head = "<head>" +
		"<style>" +
		"table {" +
		"  border-collapse: collapse;" +
		"  width: 100%;" +
		"}" +
		"th, td {" +
		"  text-align: left;" +
		"  padding: 8px;" +
		"}" +
		"tr:nth-child(even){background-color: #f2f2f2}" +
		"th {" +
		"  background-color: #4CAF50;" +
		"  color: white;" +
		"}" +
		"</style>" +
		"</head>" +
		"<body>" +
		"<h2>Tarificacion de CDRs</h2>" +
		"<table>" +
		"  <tr>" +
		"    <th>Origen</th>" +
		"    <th>Destino</th>" +
		"    <th>Fecha</th>" +
		"    <th>Hora</th>" +
		"    <th>Duración</th>" +
		"    <th>Costo</th>" +
		"  </tr>";
		String body=getCdrsTable(); 
		String end = "</table>" +
				"</body>";

		
		return head + body + end;
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
		return res;
	}

	private static String getRegistoCdrTable(RegistroCDR r) {
		String res = " <tr>" +
				"    <td>"+ r.getTelefonoOrigen() +"</td>" +
				"    <td>"+ r.getTelefonoDestino() +"</td>" +
				"    <td>"+ r.getFecha() +"</td>" +
				"    <td>"+ r.getHora() +"</td>" +
				"    <td>"+ r.getTiempoDuracionSegundos() +"</td>" +
				"    <td>"+ r.getCosto() +"</td>" +
				"</tr>";
		return res;
	}
}


