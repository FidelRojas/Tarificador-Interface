package tarificador;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private String nombre;
	private String numero;

	private String plan;
	    
	private List<String> numerosAmigos=new ArrayList<String>();
	
	public boolean anadirNumeroAmigo(String numeroAmigo) {
		if(numerosAmigos.size()<3) {
			numerosAmigos.add(numeroAmigo);
			return true;
		}
		return false;
	}
	
	public boolean esNumeroAmigo(String numero) {
		for (String item : numerosAmigos) {
		    if (item.equals(numero)) {
		    	return true;
		    }
		}
		return false;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}
	public String getNumero() {
		return numero;
	}

		  
	public void setNumero(String nuevoNumero) {
		this.numero = nuevoNumero;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}
}
