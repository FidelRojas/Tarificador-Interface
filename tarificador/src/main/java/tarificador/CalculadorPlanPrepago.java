package tarificador;

public class CalculadorPlanPrepago implements CalculadorDeCosto {

	@Override
	public double calcularCosto(RegistroCDR registro) {
		String hora = registro.getHora();
		int tiempoDuracion= registro.getTiempoDuracionSegundos();
		double tarifa = this.determinarTarifa(hora);
		double total = tiempoDuracion * tarifa/60;
		total = Math.round(total * 100.0) / 100.0;
		return total;
	}
	private double determinarTarifa(String hora) {
		// Tarifa por minuto horario normal 1.45 de 07:00 a 20:59 (minutos 420 a 1259)
		// Tarifa por minuto horario reducido 0.95 de 21:00 a 00:59 ( 1260 a
		// Tarifa por minuto horario super reducido 0.7 de 01:00 a 06:59
		String[] partes = hora.split(":");
		String H = partes[0];
		String M = partes[1];
		int h = Integer.parseInt(H);
		int m = Integer.parseInt(M);
		int horaEnMinutos = h*60 + m;
		double tarifa = 0.95;
		if(horaEnMinutos >= 420 && horaEnMinutos <= 1259 )
			tarifa = 1.45;
		if(horaEnMinutos >= 60 && horaEnMinutos <= 419)
			tarifa = 0.7;
		return tarifa;
	}
}
