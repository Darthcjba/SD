package py.una.pol.personas.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Asignatura {
	Long id;
	String nombre;
	
	List<Persona> personas;
	
	public Asignatura() {
		this.personas = new ArrayList<Persona>();
	}
	public Asignatura(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;		
		this.personas = new ArrayList<Persona>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Persona> getPersonas() {
		return personas;
	}
	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}
	
	
}
