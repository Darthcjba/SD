package py.una.pol.personas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@XmlRootElement
public class Persona  implements Serializable {

	Long cedula;
	String nombre;
	String apellido;
	
	List<String> asignaturas;
	
	public Persona(){
		asignaturas = new ArrayList<String>();
	}

	public Persona(Long pcedula, String pnombre, String papellido){
		this.cedula = pcedula;
		this.nombre = pnombre;
		this.apellido = papellido;
		
		asignaturas = new ArrayList<String>();
	}
	
	public Long getCedula() {
		return cedula;
	}

	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<String> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<String> asignaturas) {
		this.asignaturas = asignaturas;
	}
}
