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

public class FileCDRRepository implements ICDRRepository {

	private String url = "";
	private ArrayList<RegistroCDR> listCDR = new ArrayList<RegistroCDR>();
	private BufferedReader in;
	
	public FileCDRRepository() {
	}
	
	public FileCDRRepository(String url) {
		this.url=url;
	}
	
	public void connect() {
		try {
			in = new BufferedReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void dowloadDataCDR() {
		connect();
		String str = "";
		int duracion;
		
		try {
			str = in.readLine(); // skip header
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			while ((str = in.readLine()) != null) {
				String[] data = str.split(", ");
				duracion = Integer.parseInt(data[5]);
				RegistroCDR cdr = new RegistroCDR(data[1], data[2], data[3], data[4], duracion);
				listCDR.add(cdr);
			}
			in.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String tranformCDRtoString(RegistroCDR cdr) {
		String strDuracion = String.valueOf(cdr.getTiempoDuracionSegundos());
		String strCosto = String.valueOf(cdr.getCosto());
		String str = cdr.getTelefonoOrigen()+", "+cdr.getTelefonoDestino()+", "+cdr.getFecha()+", "+cdr.getHora()+", "+strDuracion+", "+strCosto;
		return str;
	}
	
	public String headerCDR() {
		return "telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo";
	}
	
	public String getStringActualTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HHmmss");  
	    Date date = new Date();
	    String strDate = formatter.format(date);  
		return strDate;
	}
	
	@Override
	public ArrayList<RegistroCDR> getList() {
		dowloadDataCDR();
		return listCDR;
	}

	@Override
	public void saveCDRsHistorial(ArrayList<RegistroCDR> lista) {
	    String ruta = "datas\\file\\Historial\\";
	    String namefileDarte = getStringActualTime();
	    String url = ruta + namefileDarte + ".txt";
	    String cdrStr = "";
	    
		File file = new File(url);
		FileOutputStream fos;
		
		try {
			fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			 
			bw.write(headerCDR());
			bw.newLine();
			
			for (int i = 0; i < lista.size() ; i++) {
				RegistroCDR cdr = lista.get(i);
				cdrStr = tranformCDRtoString(cdr);
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
}
