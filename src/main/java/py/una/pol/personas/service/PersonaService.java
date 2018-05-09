package py.una.pol.personas.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.PersonaDAO;
import py.una.pol.personas.model.Persona;
import py.una.pol.personas.model.Persona_Asignatura;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class PersonaService {

    @Inject
    private Logger log;

    @Inject
    private PersonaDAO dao;
    
    public long asociar(Persona_Asignatura r) throws Exception{
    	log.info("Asociando --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
        long op;
    	try {
        	op = dao.asociar(r);
        }catch(Exception e) {
        	log.severe("Error Asociando --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
        	throw e;
        }
    	if(op > 0) {
    		log.info("Persona_Asignatura Exito --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());	
    	}else if( op == 0) {
    		log.info("Persona_Asignatura ya existe --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
    	}else {
    		log.info("Ocurrio un Error de Transaccion");
    	}
    	return op;
    }
    
    public long desasociar(Persona_Asignatura r) throws Exception{
    	log.info("desAsociando --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
        long op;
    	try {
        	op = dao.desasociar(r);
        }catch(Exception e) {
        	log.severe("Error desAsociando --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
        	throw e;
        }
    	if(op > 0) {
    		log.info("desAsociando Exito --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());	
    	}else if( op == 0) {
    		log.info("Persona_Asignatura no existe --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
    	}else {
    		log.info("Ocurrio un Error de Transaccion");
    	}
    	return op;
    }

    public void crear(Persona p) throws Exception {
        log.info("Creando Persona: " + p.getNombre() + " " + p.getApellido());
        try {
        	dao.insertar(p);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Persona creada con éxito: " + p.getNombre() + " " + p.getApellido() );
    }
    
    public List<Persona> seleccionar() {
    	return dao.seleccionar();
    }
    
    public Persona seleccionarPorCedula(long cedula) {
    	return dao.seleccionarPorCedula(cedula);
    }
    
    public long borrar(long cedula) throws Exception {
    	return dao.borrar(cedula);
    }

	public List<String> listarAsignaturas(long cedula) {
		log.info("Listar Asignaturas | cedula: "+cedula);
        List<String> asignaturas;
		try {
        	asignaturas = dao.listarAsignaturas(cedula);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Lista asignatura creada | cedula: "+cedula);
		return asignaturas;
	}
}
