package Entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import boundaries.LectorDeCDRs;
import tarificador.RegistroCDR;

public class CargadorCDRsDesdeTXT implements LectorDeCDRs {
	
	private String url = "";
	private ArrayList<RegistroCDR> listaCDRs = new ArrayList<RegistroCDR>();
	private BufferedReader in;
	private boolean exep = false;
	@Override
	public ArrayList<RegistroCDR> cargarCDRs(String path) {
		this.url = path;
		String str = "";
		int duracion;
		

		conectar();
		if (!exep) {

			try {
				str = in.readLine(); // Saltar Cabecera
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				while ((str = in.readLine()) != null) {
					String[] data = str.split(", ");
					if (data.length == 5) {
						duracion = convertirDuracionEnFormatoCorrecto(data[4]);
						RegistroCDR cdr = new RegistroCDR(data[0], data[1], data[2], data[3], duracion);
						listaCDRs.add(cdr);
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cerrarConexion();
		}
		return listaCDRs;

	}
	private void conectar() {
		try {
			in = new BufferedReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			exep = true;
		}
	}
	
	private void cerrarConexion() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int convertirDuracionEnFormatoCorrecto(String duracion) {

		String[] partes = duracion.split(":");
		int duracionFinal = 0;
		if (partes.length == 3) {
			duracionFinal = Integer.parseInt(partes[0]) * 3600 + Integer.parseInt(partes[1]) * 60
					+ Integer.parseInt(partes[2]);
		} else if (partes.length == 2) {
			duracionFinal = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
		} else {
			duracionFinal = Integer.parseInt(duracion);
		}
		return duracionFinal;
	}


}
