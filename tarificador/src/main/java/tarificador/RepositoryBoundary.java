package tarificador;
import java.util.ArrayList;

public interface RepositoryBoundary {
	public ArrayList<RegistroCDR> getList();
	public void guardarCDRsTarificadosHistorial(ArrayList<RegistroCDR> listaCDRs);
	public ArrayList<RegistroCDR> obtenerCDRsTarificadosDe(String numeroOrigenBuscado);
	public ArrayList<RegistroCDR> obtenerCDRsTarificadasSegun(Historial historial);
	public ArrayList<Historial> obtenerHistorialDeTarificaciones();
}
