package tarificador;

public class CalculadorPlanPostpago implements CalculadorDeCosto {

	@Override
	public double calcularCosto(RegistroCDR registro) {
		// TODO Auto-generated method stub
		double total = 0;
		ListaClientes clientes= ListaClientes.getInstance();
		Cliente cliente =clientes.buscar(registro.getTelefonoOrigen());
		boolean esAmigo=false;
		if(cliente!=null) {
			esAmigo = cliente.esNumeroAmigo(registro.getTelefonoDestino());
		}
		if(!esAmigo)
		{
			int tiempoDuracion= registro.getTiempoDuracionSegundos();
			double tarifa = 1;
			total = tiempoDuracion * tarifa/60;
			total = Math.round(total * 100.0) / 100.0;
		}
		return total;
	}
	

}
