import interfaces.Interface;
import tarificador.Central;

public class Main {
	public static void main(String[] args) {
		Central central = new Central();
		Interface.run(central);
		
	}
}
