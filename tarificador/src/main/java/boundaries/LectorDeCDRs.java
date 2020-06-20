package boundaries;

import java.util.ArrayList;

import tarificador.RegistroCDR;

public interface LectorDeCDRs {
	public abstract ArrayList<RegistroCDR> cargarCDRs(String path);
}
