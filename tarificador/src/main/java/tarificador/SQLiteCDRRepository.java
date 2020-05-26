package tarificador;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;

public class SQLiteCDRRepository implements ICDRRepository {

	private String url = "";
	private Connection connect;
	private ArrayList<RegistroCDR> listCDR = new ArrayList<RegistroCDR>();
	
	public SQLiteCDRRepository(String url) {
		this.url=url;
		connect();
		cargarCDR();
		close();
	}
	
	public void connect() {
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:" + url);
			if(connect != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido conectar a la base de datos\n" + e.getMessage());
		}		
	}
	
	public void close() {
		try {
			connect.close();
		} catch (SQLException e) {
			Logger.getLogger(SQLiteCDRRepository.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void show() {
		ResultSet result = null;
		try {
			PreparedStatement st = connect.prepareStatement("select * from CDR");
			result = st.executeQuery();
			while(result.next()) {
				System.out.println("Id: ");
				System.out.println(result.getInt(1));
				
				System.out.println("Telefono Origen: ");
				System.out.println(result.getString(2));
				
				System.out.println("Telefono Destino: ");
				System.out.println(result.getString(3));
				
				System.out.println("Fecha: ");
				System.out.println(result.getString(4));
				
				System.out.println("Hora: ");
				System.out.println(result.getString(5));
				
				System.out.println("Tiempo Duracion: ");
				System.out.println(result.getString(6));
				
				System.out.println("Costo: ");
				System.out.println(result.getString(7));
				
				System.out.println("=====================================");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void cargarCDR() {
		ResultSet result = null;
		String telefonoOrigen="";
		String telefonoDestino="";
		String fecha="";
		String hora="";
		int tiempoDuracion;

		try {
			PreparedStatement st = connect.prepareStatement("select * from CDR");
			result = st.executeQuery();
			while(result.next()) {
				
				telefonoOrigen = result.getString(2);
				telefonoDestino = result.getString(3);
				fecha = result.getString(4);
				hora = result.getString(5);
				tiempoDuracion = result.getInt(6);
				
				RegistroCDR cdr = new RegistroCDR(telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion);
				listCDR.add(cdr);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public ArrayList<RegistroCDR> getList() {
		// TODO Auto-generated method stub
		return listCDR;
	}

}
