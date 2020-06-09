package tarificador;
import java.util.ArrayList;

public interface ICDRRepository {
	public ArrayList<RegistroCDR> getList();
	public void saveCDRsHistorial(ArrayList<RegistroCDR> list);
}
