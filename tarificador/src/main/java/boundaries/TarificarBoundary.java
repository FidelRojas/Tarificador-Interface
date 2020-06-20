package boundaries;

import java.util.ArrayList;

import tarificador.RegistroCDR;

public interface TarificarBoundary {
	public abstract void tarificarListaDeCDRs(ArrayList<RegistroCDR> CDRs);

}
