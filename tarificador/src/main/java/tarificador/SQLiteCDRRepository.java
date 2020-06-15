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
	private Connection conexion;
	private ArrayList<RegistroCDR> listaCDRs = new ArrayList<RegistroCDR>();
	
	public SQLiteCDRRepository(String url) {
		this.url=url;
	}
	
	public void conectar() {
		try {
			conexion = DriverManager.getConnection("jdbc:sqlite:" + url);
			if(conexion != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException e) {
			System.err.println("No se ha podido conectar a la base de datos\n" + e.getMessage());
		}		
	}
	
	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			Logger.getLogger(SQLiteCDRRepository.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void mostrarCDRsDeBD() {
		ResultSet result = null;
		try {
			PreparedStatement st = conexion.prepareStatement("select * from CDR");
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
	
	public void cargarCDRs() {
		ResultSet result = null;
		
		String telefonoOrigen="";
		String telefonoDestino="";
		String fecha="";
		String hora="";
		int tiempoDuracion;
		double costo;
		
		conectar();
		try {
			PreparedStatement st = conexion.prepareStatement("select * from CDR");
			result = st.executeQuery();
			while(result.next()) {
				
				telefonoOrigen = result.getString(2);
				telefonoDestino = result.getString(3);
				fecha = result.getString(4);
				hora = result.getString(5);
				tiempoDuracion = result.getInt(6);
				costo = result.getDouble(7);
				
				RegistroCDR cdr = new RegistroCDR(telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo);
				listaCDRs.add(cdr);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		cerrarConexion();
	}
	
	public void guardarCDRtarificado(RegistroCDR cdr) {
		String sql = "INSERT INTO CDR(telefonoOrigen, telefonoDestino, fecha,hora,tiempoDuracion,costo) VALUES(?,?,?,?,?,?)";
		try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, cdr.getTelefonoOrigen());
            pstmt.setString(2, cdr.getTelefonoDestino());
            pstmt.setString(3, cdr.getFecha());
            pstmt.setString(4, cdr.getHora());
            pstmt.setString(5, String.valueOf(cdr.getTiempoDuracionSegundos()));
            pstmt.setDouble(6, cdr.getCosto());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	
	@Override
	public ArrayList<RegistroCDR> getList() {
		// TODO Auto-generated method stub
		cargarCDRs();
		return listaCDRs;
	}

	@Override
	public void guardarCDRsTarificadosHistorial(ArrayList<RegistroCDR> listaCDRs) {
		// TODO Auto-generated method stub
		conectar();
		for (RegistroCDR cdr : listaCDRs)
		{
			guardarCDRtarificado(cdr);
		}
		cerrarConexion();
	}

	@Override
	public ArrayList<RegistroCDR> obtenerCDRsTarificadosDe(String numeroOrigenBuscado) {
		ResultSet result = null;
		
		ArrayList<RegistroCDR> listaCDRsDeUnNumero = new ArrayList<RegistroCDR>();
		String telefonoOrigen="";
		String telefonoDestino="";
		String fecha="";
		String hora="";
		int tiempoDuracion;
		double costo;
		
		String consultaSql = "select * from CDR where telefonoOrigen=" + numeroOrigenBuscado;
		conectar();
		try {
			PreparedStatement st = conexion.prepareStatement(consultaSql);
			result = st.executeQuery();
			while(result.next()) {
				
				telefonoOrigen = result.getString(2);
				telefonoDestino = result.getString(3);
				fecha = result.getString(4);
				hora = result.getString(5);
				tiempoDuracion = result.getInt(6);
				costo = result.getDouble(7);
				
				RegistroCDR cdr = new RegistroCDR(telefonoOrigen, telefonoDestino, fecha, hora, tiempoDuracion, costo);
				listaCDRsDeUnNumero.add(cdr);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		cerrarConexion();
		return listaCDRsDeUnNumero;
	}

}
