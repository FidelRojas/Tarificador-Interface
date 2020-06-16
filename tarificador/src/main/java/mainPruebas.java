
import interfaces.Interface;
import interfaces.InterfaceFacturadora;
import tarificador.Central;

public class mainPruebas {
	public static void main(String[] args) {
		Central central = new Central();
		Interface.run(central);
		//InterfaceFacturadora.run(central);
	}
}
