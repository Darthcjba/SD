package py.una.pol.personas.rest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.una.pol.personas.model.Asignatura;
import py.una.pol.personas.model.Persona;
import py.una.pol.personas.service.AsignaturaService;

@Path("/asignatura")
@RequestScoped
public class AsignaturaRESTService {
	@Inject
    private Logger log;

    @Inject 
    AsignaturaService asignaturaService;
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Asignatura> listar() {
        return asignaturaService.seleccionar();
    }
    
    @GET
    @Path("/personas/{id:[0-9][0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Persona> listarPersonas(@PathParam("id") long id) {
        return asignaturaService.asignaturaPersonas(id);
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Asignatura obtenerPorId(@PathParam("id") long id) {
        Asignatura p = asignaturaService.seleccionarPorCedula(id);
        if (p == null) {
        	log.info("obtenerPorId " + id + " no encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        log.info("obtenerPorId " + id + " encontrada: " + p.getNombre());
        return p;
    }
    
    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Asignatura obtenerPorIdQuery(@QueryParam("id") long id) {
        Asignatura p = asignaturaService.seleccionarPorCedula(id);
        if (p == null) {
        	log.info("obtenerPorId " + id + " no encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        log.info("obtenerPorId " + id + " encontrada: " + p.getNombre());
        return p;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Asignatura p) {

        Response.ResponseBuilder builder = null;

        try {
            asignaturaService.crear(p);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "exito");
            builder = Response.status(201).entity(responseData);
            
        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrar(@PathParam("id") long id)
    {      
 	   Response.ResponseBuilder builder = null;
 	   try {
 		   Map<String, String> responseData = new HashMap<>();
 		   if(asignaturaService.seleccionarPorCedula(id) == null) {
 			   responseData.put("status", "error");
 			   responseData.put("mensaje", "asignatura no existe");
 			   builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseData);
 		   }else { 			   
 			   asignaturaService.borrar(id);
 			   responseData.put("status", "exito");
			   responseData.put("mensaje", "asignatura borrada exitosamente.");
 			   builder = Response.status(202).entity(responseData);			   
 		   }		    		   
 	   } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }
}
