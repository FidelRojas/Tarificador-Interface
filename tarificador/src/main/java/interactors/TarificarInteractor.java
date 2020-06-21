package interactors;

import java.util.ArrayList;

import boundaries.TarificarBoundary;
import tarificador.CalculadorDeCosto;
import tarificador.CalculadorPlanPostpago;
import tarificador.CalculadorPlanPrepago;
import tarificador.CalculadorPlanWow;
import tarificador.ListaClientes;
import tarificador.RegistroCDR;

public class TarificarInteractor implements TarificarBoundary {
	private ListaClientes LC= ListaClientes.getInstance(); // TODO: Client list boundary here

	@Override
	public void tarificarListaDeCDRs(ArrayList<RegistroCDR> CDRs) {
		for(RegistroCDR CDR:CDRs) {
			tarificarCDR(CDR);
		}
	}
	private void tarificarCDR(RegistroCDR CDR) {
		CalculadorDeCosto calculador = this.crearCalculador(CDR);
		CDR.setCosto(calculador.calcularCosto(CDR));

	}
	
	private CalculadorDeCosto crearCalculador(RegistroCDR registro) {
		String plan = LC.getPlanDeCliente(registro.getTelefonoOrigen());
		if (plan == "Wow") {
			CalculadorDeCosto calculador = new CalculadorPlanWow();
			return calculador;
		}
		else if (plan == "PostPago") {
			CalculadorDeCosto calculador = new CalculadorPlanPostpago();
			return calculador;
		}
		else if (plan == "PrePago") {
			CalculadorDeCosto calculador = new CalculadorPlanPrepago();
			return calculador;
		}
		else {
			CalculadorDeCosto calculador = new CalculadorPlanPrepago();
			return calculador;
		}
	}

}
