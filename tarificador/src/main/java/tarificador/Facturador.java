package tarificador;

import java.util.ArrayList;

import org.apache.commons.collections.Factory;

public class Facturador implements FacturadorBoundary, Factory {

	@Override
	public Object create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calcularFactura(String numeroBuscado, int mes, RepositoryBoundary repositorio) {
		ArrayList<RegistroCDR> CDRsDeUnCliente;
		double suma = 0.0;
		CDRsDeUnCliente = repositorio.obtenerCDRsTarificadosDe(numeroBuscado);
		
		for(RegistroCDR cdr:CDRsDeUnCliente) {
			String fecha = cdr.getFecha();
			String[] partes = fecha.split("/");
			int mesCDR =Integer.parseInt(partes[1]);
			if(mesCDR == mes) {
				suma = suma + cdr.getCosto();
			}
		}
		System.out.println(suma);
		return suma;
	}

}
