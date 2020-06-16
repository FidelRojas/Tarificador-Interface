package tarificador;

public interface FacturadorBoundary {
	public abstract double calcularFactura(String numeroBuscado, int mes, RepositoryBoundary repositorio);
}
