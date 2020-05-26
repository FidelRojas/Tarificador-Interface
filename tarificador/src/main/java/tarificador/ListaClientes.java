package tarificador;

import java.util.ArrayList;
import java.util.List;

public class ListaClientes {
	private static ListaClientes instanciaUnica;
	private ListaClientes() {}
	private synchronized static void createInstance() {
        if (instanciaUnica == null) { 
            instanciaUnica = new ListaClientes();
        }
    }
 
    public static ListaClientes getInstance() {
        createInstance();

        return instanciaUnica;
    }
	private List<Cliente> clientes=new ArrayList<Cliente>();  

	public Cliente buscar(String numero) {
		for(Cliente cliente : clientes) {
			if(cliente.getNumero()==numero) {
				return cliente;
			}
		}
		return null;
	}
	public void añadir(Cliente nuevoCliente) {
		clientes.add(nuevoCliente);
	}
	
	
	
}
