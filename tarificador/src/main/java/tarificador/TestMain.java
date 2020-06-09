package tarificador;


public class TestMain {
	public static void main(String[] args) {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		nuevoCliente.setPlan("PrePago");
		ListaClientes LC= ListaClientes.getInstance();
		LC.añadir(nuevoCliente);
		
		RegistroCDR testCDR = new RegistroCDR(); 
		testCDR.setTelefonoOrigen("77777777");
		testCDR.setTelefonoDestino("78844888");
		testCDR.setFecha("2020");
		testCDR.setHora("19:00");
		testCDR.setTiempoDuracionSegundos(155);
		
		Tarificador tarificador = new Tarificador();
		tarificador.setRegistro(testCDR);
		tarificador.calcularCostoLlamada();
		
		Central central = new Central();
		double resultado = central.tarificarCDR(testCDR);
		System.out.println(resultado);;
		central.cambiarConfiguracion("persistencia", "baseDeDatos");
		central.cargarCDRsDesdeTexto("D:\\CDRs.txt");
		central.debugMostrar();
	}
}
