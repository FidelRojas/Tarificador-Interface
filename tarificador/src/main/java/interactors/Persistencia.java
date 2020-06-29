package interactors;

import boundaries.IPersistencia;
import tarificador.FileCDRRepository;
import tarificador.RepositoryBoundary;
import tarificador.SQLiteCDRRepository;

public class Persistencia implements IPersistencia {
	private String configuracionDePersistencia = "SQL";
	private RepositoryBoundary repositorio = null;

	@Override
	public String configurarPersistencia(String persistencia) {
		this.configuracionDePersistencia=persistencia;
		
		if(persistencia.equals("TXT")) {
			repositorio = new FileCDRRepository();
			this.configuracionDePersistencia=persistencia;
		}
		else if(persistencia.equals("SQL")) {
			repositorio = new SQLiteCDRRepository();
			this.configuracionDePersistencia=persistencia;
		}
		return configuracionDePersistencia;
	}

}
