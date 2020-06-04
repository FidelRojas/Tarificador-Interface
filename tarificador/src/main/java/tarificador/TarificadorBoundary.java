package tarificador;

public interface TarificadorBoundary {
	public double calcularCostoLlamada();
	public void setRegistro(RegistroCDR registro);
	abstract CalculadorDeCosto crearCalculador();
}
