package tarificador;
import java.util.ArrayList;

public interface ICDRRepository {
	public ArrayList<RegistroCDR> getList();
	public ArrayList<RegistroCDR> obtenerCDRsTarificadosDe(String numeroOrigenBuscado);
	public void guardarCDRsTarificadosHistorial(ArrayList<RegistroCDR> listaCDRs);
}
