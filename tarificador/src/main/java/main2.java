import java.util.ArrayList;

import tarificador.FileCDRRepository;
import tarificador.ICDRRepository;
import tarificador.RegistroCDR;

public class main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\sql\\dataBaseCentral.db";
		String url2 = "E:\\U.C.B\\My Workspace\\Proyecto Arqui2\\Tarificador-Interface\\tarificador\\datas\\file\\CDR.txt";
		//ICDRRepository c = new SQLiteCDRRepository(url);
		ICDRRepository c = new FileCDRRepository(url2);
		ArrayList<RegistroCDR> listCDR = c.getList();
		RegistroCDR registro;
		
		for (int i=0;i<listCDR.size();i++) {
			registro=listCDR.get(i);
			
			System.out.println("Origen: " + registro.getTelefonoOrigen() + " Destino: " + registro.getTelefonoDestino() + " Fecha: " + registro.getFecha() + " Hora: " + registro.getHora() + " Duracion: " + registro.getTiempoDuracionSegundos() + " Costo: " + registro.getCosto());
		}
		
	}

}
