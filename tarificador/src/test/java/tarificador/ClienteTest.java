package tarificador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void SetYGetNombreCliente() {
		Cliente test = new Cliente(); 
		test.setNombre("Pedro");
		assertEquals("Pedro", test.getNombre(), "Esperamos Pedro");
	}
	
	@Test
	void SetYGetNumeroCliente() {
		Cliente test = new Cliente(); 
		test.setNumero("71111111");
		assertEquals("71111111", test.getNumero(), "Esperamos 71111111");
	}
	
	@Test
	void AñadirCliente() {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		ListaClientes test= ListaClientes.getInstance();
		test.añadir(nuevoCliente);
		assertEquals(nuevoCliente.getNumero(), test.buscar("77777777").getNumero(), "Esperamos que encuentre el cliente con numer 77777777");
	}
	
	@Test
	void BuscarClienteEnListaQuenoExixte() {
		Cliente nuevoCliente = new Cliente(); 
		nuevoCliente.setNumero("77777777");
		ListaClientes test= ListaClientes.getInstance();
		test.añadir(nuevoCliente);
		assertEquals(null, test.buscar("78888888"), "Esperamos que no encuentre y devuelva null");
	}

}
