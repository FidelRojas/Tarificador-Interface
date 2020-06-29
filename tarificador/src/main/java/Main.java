
import Entities.CargadorCDRsDesdeTXT;
import boundaries.LectorDeCDRs;
import controllers.ControladorCargarCDRs;
import interfaces.Interface;
import tarificador.Central;
import tarificador.FileCDRRepository;
import tarificador.SQLiteCDRRepository;

public class Main {
	public static void main(String[] args) {
		Central central = new Central();
		FileCDRRepository fileRepository = new FileCDRRepository();
		LectorDeCDRs lectorCDRs = new CargadorCDRsDesdeTXT();
		SQLiteCDRRepository sqliteRepository = new SQLiteCDRRepository();
		ControladorCargarCDRs controlador = new ControladorCargarCDRs();
		controlador.setRepository(lectorCDRs);
		//central.setControladorCargarCDRs(controlador);
		Interface.run(central);
		
	}
}
