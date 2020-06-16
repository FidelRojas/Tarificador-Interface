package tarificador;

public interface TarificadorBoundary {
	public double calcularCostoLlamada(RegistroCDR registro);
	abstract CalculadorDeCosto crearCalculador(RegistroCDR registro);
}
