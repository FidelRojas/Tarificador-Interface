package tarificador;

public class CalculadorPlanWow implements CalculadorDeCosto {

	@Override
	public double calcularCosto(RegistroCDR registro) {
		double total=0;
		double tarifa = 1;
		long segundo=registro.getTiempoDuracionSegundos();
				
		ListaClientes clientes= ListaClientes.getInstance();
		if(!clientes.buscar(registro.getTelefonoOrigen()).esNumeroAmigo(registro.getTelefonoDestino())){
			total=tarifa*segundo/60;
		}
		return total;
	}
	
}
