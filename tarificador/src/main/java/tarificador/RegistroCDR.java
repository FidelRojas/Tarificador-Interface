package tarificador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class RegistroCDR {
	private String telefonoOrigen;
	private String telefonoDestino;
	private String fecha;
	private String hora;
	private int tiempoDuracionSegundo;
	private double costo;
	public String getTelefonoOrigen() {
		return telefonoOrigen;
	}
	public void setTelefonoOrigen(String telefonoOrigen) {
		this.telefonoOrigen = telefonoOrigen;
	}
	public String getTelefonoDestino() {
		return telefonoDestino;
	}
	public void setTelefonoDestino(String telefonoDestino) {
		this.telefonoDestino = telefonoDestino;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getTiempoDuracionSegundos() {
		return tiempoDuracionSegundo;
	}
	public void setTiempoDuracionSegundos(int tiempoDuracionSegundo) {
		this.tiempoDuracionSegundo = tiempoDuracionSegundo;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String retornarCadenaCDR(String separador){
		String cadena= telefonoOrigen+separador+telefonoDestino+separador+fecha +separador+hora+separador+tiempoDuracionSegundo+separador+costo;
		return cadena;
	}
	public void registrarCDRaFichero() {
		String nombreFichero="CDR.txt";
		String cadena= retornarCadenaCDR(" ");
		
		File f;
		FileWriter w;
		BufferedWriter bw;
		PrintWriter wr;
		
		try {
			f= new File(nombreFichero);
			w= new FileWriter(f,true);
			bw=new BufferedWriter(w);
			wr=new PrintWriter(bw);
			
			wr.append(cadena+"\n");
			
			wr.close();
			bw.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ha sucedido un error"+e);
		}
		
	}
}
