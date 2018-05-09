package py.una.pol.personas.model;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Persona_Asignatura {
	Long id;
	Long id_persona;
	Long id_asignatura;
			
	public Persona_Asignatura() {
		super();
	}

	public Persona_Asignatura(Long id, Long id_persona, Long id_asignatura) {
		super();
		this.id = id;
		this.id_persona = id_persona;
		this.id_asignatura = id_asignatura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_persona() {
		return id_persona;
	}

	public void setId_persona(Long id_persona) {
		this.id_persona = id_persona;
	}

	public Long getId_asignatura() {
		return id_asignatura;
	}

	public void setId_asignatura(Long id_asignatura) {
		this.id_asignatura = id_asignatura;
	}
	
	
}
