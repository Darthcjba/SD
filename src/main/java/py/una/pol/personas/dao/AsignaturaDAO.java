package py.una.pol.personas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.model.Asignatura;
import py.una.pol.personas.model.Persona;

@Stateless
public class AsignaturaDAO {
	@Inject
    private Logger log;
	
	public List<Asignatura> seleccionar() {
		String query = "SELECT id, nombre FROM asignatura ";
		
		List<Asignatura> lista = new ArrayList<Asignatura>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Asignatura asig = new Asignatura();
        		asig.setId(rs.getLong(1));
        		asig.setNombre(rs.getString(2));
        		lista.add(asig);
        	}
        	
        } catch (SQLException ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
	public Asignatura seleccionarPorId(long id) {
		String SQL = "SELECT id, nombre FROM asignatura WHERE id = ? ";
		
		Asignatura a = null;
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, id);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		a = new Asignatura();
        		a.setId(rs.getLong(1));
        		a.setNombre(rs.getString(2));
        	}
        	
        } catch (SQLException ex) {
        	log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return a;

	}
	
	public long insertar(Asignatura p) throws SQLException {

        String SQL = "INSERT INTO asignatura(nombre) "
                + "VALUES(?)";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);            
            pstmt.setString(1, p.getNombre());           
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        	
        return id;    	    	
    }
	
	public long actualizar(Asignatura p) throws SQLException {

        String SQL = "UPDATE asignatura SET nombre = ? WHERE id = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre());
            pstmt.setLong(2, p.getId());
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }
	
	public long borrar(long idAsignatura) throws SQLException {

        String SQL = "DELETE FROM asignatura WHERE id = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, idAsignatura);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	log.severe("Error en la eliminación: " + ex.getMessage());
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la eliminación: " + ex.getMessage());
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        		throw ef;
        	}
        }
        return id;
    }

	public List<Persona> asignaturaPersonas(long id) {
		String SQL = "SELECT cedula, nombre, apellido FROM persona WHERE cedula IN (SELECT id_persona FROM persona_asignatura WHERE id_asignatura = ?) ";
		List<Persona> list = new ArrayList<Persona>();
		
		Connection conn = null;
		Persona p;
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, id);        	
        	ResultSet rs = pstmt.executeQuery();
        	while(rs.next()) {
        		p = new Persona();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
        		list.add(p);
        	}
        	
        } catch (SQLException ex) {
        	log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return list;				
	}
}
