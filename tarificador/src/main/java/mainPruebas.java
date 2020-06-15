
import java.util.ArrayList;

import tarificador.FileCDRRepository;
import tarificador.ICDRRepository;
import tarificador.RegistroCDR;
import tarificador.SQLiteCDRRepository;

public class mainPruebas {
	public static void main(String[] args) {
		//pruebaMostrarCDRs();
		//pruebaHistorialCDR();
		//pruebaTenerCDRsProvenientesDeUnNumero();
		//pruebaGuardarCDRsSQL();
	}
	
	public static void pruebaGuardarCDRsSQL() {
		String url="datas\\sql\\dataBaseCentral.db";
		SQLiteCDRRepository SQLr = new SQLiteCDRRepository(url);
		
		RegistroCDR a = new RegistroCDR("1","2","25/12/2021","22:00",100);
	    a.setCosto(20);
	    ArrayList<RegistroCDR> list = new ArrayList<RegistroCDR>();
	    list.add(a);
	    list.add(a);
	    SQLr.guardarCDRsTarificadosHistorial(list);
	}
	
	public static void pruebaTenerCDRsProvenientesDeUnNumero() {
		String url="datas\\sql\\dataBaseCentral.db";
		ICDRRepository c = new SQLiteCDRRepository(url);
		
		//String url2 = "datas\\file\\CDR.txt";
		//ICDRRepository c = new FileCDRRepository(url2);
		ArrayList<RegistroCDR> listCDR = c.obtenerCDRsTarificadosDe("70442222");
		RegistroCDR registro;
		
		for (int i=0;i<listCDR.size();i++) {
			registro=listCDR.get(i);
			System.out.println("Origen: " + registro.getTelefonoOrigen() + " Destino: " + registro.getTelefonoDestino() + " Fecha: " + registro.getFecha() + " Hora: " + registro.getHora() + " Duracion: " + registro.getTiempoDuracionSegundos() + " Costo: " + registro.getCosto());
		}
	}
	
	public static void pruebaHistorialCDR() {
		ICDRRepository c = new FileCDRRepository();
		
	    RegistroCDR a = new RegistroCDR("546115","1665131","18/07/2021","14:45",14,15.12);
	    ArrayList<RegistroCDR> list = new ArrayList<RegistroCDR>();
	    list.add(a);
	    list.add(a);
	    list.add(a);  
	    c.guardarCDRsTarificadosHistorial(list);
	}
	
	public static void pruebaMostrarCDRs() {
		String url="E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\sql\\dataBaseCentral.db";
		ICDRRepository c = new SQLiteCDRRepository(url);
		
		//String url2 = "E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		//ICDRRepository c = new FileCDRRepository(url2);
		
		ArrayList<RegistroCDR> listCDR = c.getList();
		for (RegistroCDR cdr : listCDR)
		{
			System.out.println("Origen: " + cdr.getTelefonoOrigen() + " Destino: " + cdr.getTelefonoDestino() + " Fecha: " + cdr.getFecha() + " Hora: " + cdr.getHora() + " Duracion: " + cdr.getTiempoDuracionSegundos() + " Costo: " + cdr.getCosto());
		}
	}
}

