package tarificador;
import java.util.ArrayList;

public interface ICDRRepository {
	public ArrayList<RegistroCDR> getList();
	public ArrayList<RegistroCDR> getCDRfrom(String numeroOrigen);
	public void saveCDRsHistorial(ArrayList<RegistroCDR> list);
}
