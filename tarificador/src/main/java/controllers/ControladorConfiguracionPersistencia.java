package controllers;

import boundaries.IPersistencia;

public class ControladorConfiguracionPersistencia {
	
	private IPersistencia persistenciaBound =null;

	
	public void setRepository(IPersistencia persistenciaBound) {
		this.persistenciaBound = persistenciaBound;
	}
	
	public void cambiarConfiguracionDePersistencia(String value) {
		persistenciaBound.configurarPersistencia(value);
	}
}
