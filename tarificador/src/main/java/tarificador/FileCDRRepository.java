package tarificador;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCDRRepository implements ICDRRepository {

	private String url = "";
	private ArrayList<RegistroCDR> listCDR = new ArrayList<RegistroCDR>();
	private BufferedReader in;
	
	public FileCDRRepository(String url) {
		this.url=url;
		dowloadDataCDR();
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<RegistroCDR> getList() {
		return listCDR;
	}

}
