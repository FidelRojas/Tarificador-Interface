
import java.util.ArrayList;

import tarificador.FileCDRRepository;
import tarificador.Historial;
import tarificador.ICDRRepository;
import tarificador.RegistroCDR;
import tarificador.SQLiteCDRRepository;

public class mainPruebas {
	public static void main(String[] args) {
		//pruebaMostrarCDRs();
		//pruebaHistorialCDR();
		//pruebaTenerCDRsProvenientesDeUnNumero();
		//pruebaGuardarCDRsSQL();
		//pruebaGuardarHistorialSql();
		//obtenerHistorialesDeTarificaciones();
		//pruebaObtenerCDRdeUnHistorial();
		//pruebaObtenerNombreDeFicheros();
		pruebaObtenerCDRsTarificadasPorHistorial();
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
		ArrayList<RegistroCDR> listCDR = c.obtenerCDRsTarificadosDe("68442222");
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
	
	public static void pruebaGuardarHistorialSql() {
		String url="E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\sql\\dataBaseCentral.db";
		SQLiteCDRRepository c = new SQLiteCDRRepository(url);
		c.registrarFechaHistorial();
	}
	
	public static void obtenerHistorialesDeTarificaciones() {
		String url="E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\sql\\dataBaseCentral.db";
		SQLiteCDRRepository c = new SQLiteCDRRepository(url);
		
		ArrayList<Historial> historiales = c.obtenerHistorialDeTarificaciones();
		for (Historial historial : historiales)
		{
			System.out.println("Id: " + historial.getId() + " Fecha Hora: " + historial.getFechaHora());
		}
	}
	
	public static void pruebaObtenerCDRdeUnHistorial() {
		String url="E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\sql\\dataBaseCentral.db";
		SQLiteCDRRepository c = new SQLiteCDRRepository(url);
		
		Historial h = new Historial(1,"44665");
		ArrayList<RegistroCDR> listCDR = c.obtenerCDRsTarificadasSegun(h);
		for (RegistroCDR cdr : listCDR)
		{
			System.out.println("Origen: " + cdr.getTelefonoOrigen() + " Destino: " + cdr.getTelefonoDestino() + " Fecha: " + cdr.getFecha() + " Hora: " + cdr.getHora() + " Duracion: " + cdr.getTiempoDuracionSegundos() + " Costo: " + cdr.getCosto());
		}
	}
	
	public static void pruebaObtenerNombreDeFicheros() {
		String url2 = "E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		FileCDRRepository c = new FileCDRRepository(url2);
		ArrayList<Historial> historiales = c.obtenerHistorialDeTarificaciones();
		for (Historial historial : historiales)
		{
			System.out.println("Id: " + historial.getId() + " Fecha Hora: " + historial.getFechaHora());
		}
	}
	
	public static void pruebaObtenerCDRsTarificadasPorHistorial() {
		String url2 = "E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		FileCDRRepository c = new FileCDRRepository(url2);
		Historial h = new Historial(1,"14/06/2020 20:59");
		ArrayList<RegistroCDR> listCDR = c.obtenerCDRsTarificadasSegun(h);
		
		for (RegistroCDR cdr : listCDR)
		{
			System.out.println("Origen: " + cdr.getTelefonoOrigen() + " Destino: " + cdr.getTelefonoDestino() + " Fecha: " + cdr.getFecha() + " Hora: " + cdr.getHora() + " Duracion: " + cdr.getTiempoDuracionSegundos() + " Costo: " + cdr.getCosto());
		}
	}
}

