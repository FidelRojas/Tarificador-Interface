
import java.util.ArrayList;

import tarificador.FileCDRRepository;
import tarificador.ICDRRepository;
import tarificador.RegistroCDR;

public class maindate {
	public static void main(String[] args) {
		pruebaHistorialCDR();
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

