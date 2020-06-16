package tarificador;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class FileCDRRepository implements RepositoryBoundary {

	private String url = "";
	private ArrayList<RegistroCDR> listaCDRs = new ArrayList<RegistroCDR>();
	private BufferedReader in;
	
	public FileCDRRepository() {
	}
	
	public FileCDRRepository(String url) {
		this.url=url;
	}
	
	public void conectar() {
		try {
			in = new BufferedReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarConexion() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String convertirNombreEnFormatoFecha(String fecha) {
		String strFecha = fecha.substring(0, fecha.lastIndexOf("."));
		String[] partes = strFecha.split(",");
		partes[0] = partes[0].replace('-','/');
		partes[1] = partes[1].replace('.',':');
		return partes[0] + " " + partes[1];
	}
	
	public String convertirFechaEnFormatoTxt(String fecha) {
		fecha = fecha.replace('/', '-').replace(':', '.').replace(' ', ',');
		return fecha + ".txt";
	}
	public int convertirDuracionEnFormatoCorrecto(String duracion) {
		
		String[] partes = duracion.split(":");
		int duracionFinal=0;
		if(partes.length==3) {
			duracionFinal=Integer.parseInt(partes[0])*3600+Integer.parseInt(partes[1])*60+Integer.parseInt(partes[2]);
		}else if(partes.length==2) {
			duracionFinal=Integer.parseInt(partes[0])*60+Integer.parseInt(partes[1]);
		}else {
			duracionFinal=Integer.parseInt(duracion);
		}
		return duracionFinal;
	}
	
	public void cargarCDRs() {
		String str = "";
		int duracion;
		
		conectar();
		try {
			str = in.readLine(); // Saltar Cabecera
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		try {
			while ((str = in.readLine()) != null) {
				String[] data = str.split(", ");
				duracion = convertirDuracionEnFormatoCorrecto(data[5]);
				RegistroCDR cdr = new RegistroCDR(data[1], data[2], data[3], data[4], duracion);
				listaCDRs.add(cdr);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}
	
	public String transformarCDRaString(RegistroCDR cdr) {
		String strDuracion = String.valueOf(cdr.getTiempoDuracionSegundos());
		String strCosto = String.valueOf(cdr.getCosto());
		String str = cdr.getTelefonoOrigen()+", "+cdr.getTelefonoDestino()+", "+cdr.getFecha()+", "+cdr.getHora()+", "+strDuracion+", "+strCosto;
		return str;
	}
	
	public String obtenerCabeceraCDR() {
		return "telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo";
	}
	
	public String obtenerFechaActualEnString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH.mm");  
	    Date date = new Date();
	    String strDate = formatter.format(date);  
		return strDate;
	}
	
	@Override
	public ArrayList<RegistroCDR> getList() {
		cargarCDRs();
		return listaCDRs;
	}

	@Override
	public void guardarCDRsTarificadosHistorial(ArrayList<RegistroCDR> listaCDRs) {
	    String ruta = "datas\\file\\Historial\\";
	    String tituloTiempo = obtenerFechaActualEnString();
	    String url = ruta + tituloTiempo + ".txt";
	    String cdrStr = "";
	    
		File file = new File(url);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			 
			bw.write(obtenerCabeceraCDR()); //Escribir en el file la cabecera
			bw.newLine();
			for (int i = 0; i < listaCDRs.size() ; i++) {
				RegistroCDR cdr = listaCDRs.get(i);
				cdrStr = transformarCDRaString(cdr);
				bw.write(cdrStr);
				bw.newLine();
			}
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<RegistroCDR> obtenerCDRsTarificadosDe(String numeroOrigenBuscado) {
		
		ArrayList<RegistroCDR> listaCDRsDeUnNumero = new ArrayList<RegistroCDR>();
		String str = "";
		int duracion;
		
		conectar();
		try {
			str = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		try {
			while ((str = in.readLine()) != null) {
				String[] data = str.split(", ");
				String telefonoOrigen = data[1];
				
				if(telefonoOrigen.equals(numeroOrigenBuscado)) {
					duracion = Integer.parseInt(data[5]);
					RegistroCDR cdr = new RegistroCDR(data[1], data[2], data[3], data[4], duracion);
					listaCDRsDeUnNumero.add(cdr);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		
		return listaCDRsDeUnNumero;
	}

	@Override
	public ArrayList<Historial> obtenerHistorialDeTarificaciones() {
		// TODO Auto-generated method stub
		
		File folder = new File("datas\\file\\Historial");
		File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));
		ArrayList<Historial> historiles = new ArrayList<Historial>();
		String fecha = "";
		
		for (int i = 0; i < listOfFiles.length; i++) {
			fecha = convertirNombreEnFormatoFecha(listOfFiles[i].getName());
			Historial historial = new Historial(i+1, fecha);
			historiles.add(historial);
		}	
		return historiles;
	}

	@Override
	public ArrayList<RegistroCDR> obtenerCDRsTarificadasSegun(Historial historial) {
		// TODO Auto-generated method stub
		String nombreFichero = convertirFechaEnFormatoTxt(historial.getFechaHora());
		System.out.println(nombreFichero);
		ArrayList<RegistroCDR> lista = new ArrayList<RegistroCDR>();
		
		String str = "";
		int duracion;
		double costo;
		
		
		try {
			in = new BufferedReader(new FileReader("datas\\file\\Historial\\" + nombreFichero));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			str = in.readLine(); // Saltar Cabecera
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		try {
			while ((str = in.readLine()) != null) {
				String[] data = str.split(", ");
				duracion = Integer.parseInt(data[4]);
				costo = Double.parseDouble(data[5]);
				RegistroCDR cdr = new RegistroCDR(data[0], data[1], data[2], data[3], duracion, costo);
				lista.add(cdr);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		
		return lista;
	}
}
