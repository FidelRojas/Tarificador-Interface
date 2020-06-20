package controllers;
import java.util.ArrayList;

import Boundaries.LectorDeCDRs;
import tarificador.RegistroCDR;

public class ControladorCargarCDRs {
	private LectorDeCDRs repositorio =null;

	
	public void setRepository(LectorDeCDRs repositorio) {
		this.repositorio = repositorio;
	}
	
	public ArrayList<RegistroCDR> cargarCDR(String path) {
		return repositorio.cargarCDRs(path);
	}
}
