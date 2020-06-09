
import java.util.ArrayList;

import tarificador.FileCDRRepository;
import tarificador.ICDRRepository;
import tarificador.RegistroCDR;
import tarificador.SQLiteCDRRepository;

public class maindate {
	public static void main(String[] args) {
		//pruebaHistorialCDR();
		pruebaTenerCDRsProvenientesDeUnNumero();
	}
	
	public static void pruebaTenerCDRsProvenientesDeUnNumero() {
		//String url="datas\\sql\\dataBaseCentral.db";
		String url2 = "datas\\file\\CDR.txt";
		//ICDRRepository c = new SQLiteCDRRepository(url);
		ICDRRepository c = new FileCDRRepository(url2);
		ArrayList<RegistroCDR> listCDR = c.getCDRfrom("70442222");
		RegistroCDR registro;
		
		for (int i=0;i<listCDR.size();i++) {
			registro=listCDR.get(i);
			System.out.println("Origen: " + registro.getTelefonoOrigen() + " Destino: " + registro.getTelefonoDestino() + " Fecha: " + registro.getFecha() + " Hora: " + registro.getHora() + " Duracion: " + registro.getTiempoDuracionSegundos() + " Costo: " + registro.getCosto());
		}
	}
	
	public static void pruebaHistorialCDR() {
		ICDRRepository c = new FileCDRRepository();
	    RegistroCDR a = new RegistroCDR("546115","1665131","18/07/2021","14:45",14);
	    a.setCosto(15.12);
	    ArrayList<RegistroCDR> list = new ArrayList<RegistroCDR>();
	    list.add(a);
	    list.add(a);
	    list.add(a);
	    list.add(a);
	    list.add(a);
	    list.add(a);
	    
	    c.saveCDRsHistorial(list);
	}
}

