package tarificador;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
	
	public String obtenerFechaActualEnString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
	    Date date = new Date();
	    String strDate = formatter.format(date);  
		return strDate;
	}
	
	public void cargarCDRs() {
		String consultaSql = "select * from CDR";
		
		conectar();
		listaCDRs = obtenerCDRsPorSql(consultaSql);
		cerrarConexion();
	}
	
	public void registrarFechaHistorial() {
		String consultaSql = "INSERT INTO HISTORIAL(fecha) VALUES(?)";
		
		try{
            PreparedStatement pstmt = conexion.prepareStatement(consultaSql);    
            pstmt.setString(1, obtenerFechaActualEnString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public int obtenerIdUltimaFechaHistorial() {
		String consultaSql = "SELECT * FROM HISTORIAL WHERE id = (SELECT MAX(id) FROM HISTORIAL)";
		int ultimoID = 0;
		ResultSet result = null;
		
		try {
			PreparedStatement st = conexion.prepareStatement(consultaSql);
			result = st.executeQuery();
			ultimoID = result.getInt(1);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}	
		return ultimoID;
	}
	
	public ArrayList<RegistroCDR> obtenerCDRsPorSql(String consultaSql){
		ResultSet result = null;
		ArrayList<RegistroCDR> lista = new ArrayList<RegistroCDR>();
		String telefonoOrigen="";
		String telefonoDestino="";
		String fecha="";
		String hora="";
		int tiempoDuracion;
		double costo;
		
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
				lista.add(cdr);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return lista;
	}
	
	public void guardarCDRtarificado(RegistroCDR cdr, int idFecha) {
		String sql = "INSERT INTO CDRS_TARIFICADAS(telefonoOrigen, telefonoDestino, fecha,hora,duracion,costo, idHistorial) VALUES(?,?,?,?,?,?,?)";
		
		try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, cdr.getTelefonoOrigen());
            pstmt.setString(2, cdr.getTelefonoDestino());
            pstmt.setString(3, cdr.getFecha());
            pstmt.setString(4, cdr.getHora());
            pstmt.setString(5, String.valueOf(cdr.getTiempoDuracionSegundos()));
            pstmt.setDouble(6, cdr.getCosto());
            pstmt.setInt(7, idFecha);
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
		registrarFechaHistorial();
		int idFecha = obtenerIdUltimaFechaHistorial();
		for (RegistroCDR cdr : listaCDRs)
		{
			guardarCDRtarificado(cdr, idFecha);
		}
		cerrarConexion();
	}

	@Override
	public ArrayList<RegistroCDR> obtenerCDRsTarificadosDe(String numeroOrigenBuscado) {
		// TODO Auto-generated method stub
		String consultaSql = "select * from CDRS_TARIFICADAS where telefonoOrigen=" + numeroOrigenBuscado;
		
		conectar();
		ArrayList<RegistroCDR> listaCDRsDeUnNumero = obtenerCDRsPorSql(consultaSql);
		cerrarConexion();
		return listaCDRsDeUnNumero;
	}
	
	@Override
	public ArrayList<RegistroCDR> obtenerCDRsTarificadasSegun(Historial historial) {
		// TODO Auto-generated method stub
		String consultaSql = "select * from CDRS_TARIFICADAS where idHistorial=" + historial.getId();
		
		conectar();
		ArrayList<RegistroCDR> lista = obtenerCDRsPorSql(consultaSql);
		cerrarConexion();
		return lista;
	}

	@Override
	public ArrayList<Historial> obtenerHistorialDeTarificaciones() {
		// TODO Auto-generated method stub
		ResultSet result = null;
		ArrayList<Historial> historiles = new ArrayList<Historial>();
		int id;
		String fecha = "";
		String consultaSql = "select * from HISTORIAL";
		
		conectar();
		try {
			PreparedStatement st = conexion.prepareStatement(consultaSql);
			result = st.executeQuery();
			while(result.next()) {
				
				id = result.getInt(1);
				fecha = result.getString(2);
				
				Historial historial = new Historial(id, fecha);
				historiles.add(historial);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		cerrarConexion();
		return historiles;
	}
}
