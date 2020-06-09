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
	public String getPlanDeCliente(String numeroBuscado) {
		String numeroCliente;
		for(Cliente cliente : clientes) {
			numeroCliente = cliente.getNumero();
			if(numeroCliente.contentEquals(numeroBuscado)) {
				return cliente.getPlan();
			}
		}
		System.out.println("#"+numeroBuscado+"#"+ " Not found");
		return "PrePago";
	}
	public void mostrarNumeros() {
		for(Cliente cliente : clientes) {
			System.out.println(cliente.getNumero());
		}
	}
	public void añadir(Cliente nuevoCliente) {
		clientes.add(nuevoCliente);
	}
	
	
	
}
