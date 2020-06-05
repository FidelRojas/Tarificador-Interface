package tarificador;

public interface ListaClientesBoundary {
	private abstract ListaClientes() ;
	private synchronized static void createInstance();
 
    public abstract static ListaClientes getInstance();
	public abstract Cliente buscar(String numero) ;
	public abstract void añadir(Cliente nuevoCliente);
}
