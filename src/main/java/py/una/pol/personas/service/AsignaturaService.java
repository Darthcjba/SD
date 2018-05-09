package py.una.pol.personas.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.AsignaturaDAO;
import py.una.pol.personas.dao.PersonaDAO;
import py.una.pol.personas.model.Asignatura;
import py.una.pol.personas.model.Persona;

@Stateless
public class AsignaturaService {
	@Inject
    private Logger log;
	
	@Inject
    private AsignaturaDAO dao;
	
	public void crear(Asignatura p) throws Exception {
        log.info("Creando Asignatura: " + p.getNombre());
        try {
        	dao.insertar(p);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Asignatura creada con éxito: " + p.getNombre());
    }
	
	public List<Asignatura> seleccionar() {
    	return dao.seleccionar();
    }
	
	public Asignatura seleccionarPorCedula(long id) {
    	return dao.seleccionarPorId(id);
    }
    
    public long borrar(long id) throws Exception {
    	return dao.borrar(id);
    }

	public List<Persona> asignaturaPersonas(long id) {
		log.info("Lista Personas | asignaturaid: " + id);
        List<Persona> list;
		try {
        	list = dao.asignaturaPersonas(id);
        }catch(Exception e) {
        	log.severe("ERROR al listar personas | asignaturaid: " + id );
        	throw e;
        }
        log.info("Listar Personas exito | asignaturaid: " + id);
		
        return list;
	}
}
