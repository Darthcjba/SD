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

import py.una.pol.personas.model.Persona;
import py.una.pol.personas.model.Persona_Asignatura;

@Stateless
public class PersonaDAO {
 
	
    @Inject
    private Logger log;
    
	/**
	 * 
	 * @param condiciones 
	 * @return
	 */
	public List<Persona> seleccionar() {
		String query = "SELECT cedula, nombre, apellido FROM persona ";
		
		List<Persona> lista = new ArrayList<Persona>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Persona p = new Persona();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
        		
        		lista.add(p);
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
	
	public Persona seleccionarPorCedula(long cedula) {
		String SQL = "SELECT cedula, nombre, apellido FROM persona WHERE cedula = ? ";
		
		Persona p = null;
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, cedula);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		p = new Persona();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
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
		return p;

	}
	
	
    public long insertar(Persona p) throws SQLException {

        String SQL = "INSERT INTO persona(cedula, nombre, apellido) "
                + "VALUES(?,?,?)";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, p.getCedula());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getApellido());
 
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
	
    public long asociar(Persona_Asignatura r) throws SQLException{
    	long id = -1;
        Connection conn = null;
    	String sql = "SELECT id, id_persona, id_asignatura FROM persona_asignatura WHERE id_persona = ? AND id_asignatura = ?";
    	String insertSQL = "INSERT INTO persona_asignatura (id_persona, id_asignatura) VALUES ( ?, ?)";
    	try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, r.getId_persona());
            pstmt.setLong(2, r.getId_asignatura());
            
            boolean existPersona_Asignatura = false;
            int affectedRows = 0;
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()) {	
            	id = 0;
            	existPersona_Asignatura = true;
            }
            PreparedStatement pstmtIns = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            if(!existPersona_Asignatura) {            	
            	pstmtIns.setLong(1,r.getId_persona());
            	pstmtIns.setLong(2, r.getId_asignatura());
            	affectedRows = pstmtIns.executeUpdate();
            }
            
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rss = pstmtIns.getGeneratedKeys()) {
                    if (rss.next()) {
                        id = rss.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
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
    
    public long desasociar(Persona_Asignatura r) throws SQLException{
    	long id = -1;
        Connection conn = null;
    	String sql = "SELECT id, id_persona, id_asignatura FROM persona_asignatura WHERE id_persona = ? AND id_asignatura = ?";
    	String insertSQL = "DELETE FROM persona_asignatura WHERE id_persona = ? AND id_asignatura = ?";
    	try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, r.getId_persona());
            pstmt.setLong(2, r.getId_asignatura());
            
            boolean existPersona_Asignatura = false;
            int affectedRows = 0;
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()) {	            	
            	existPersona_Asignatura = true;
            }else {		id = 0;}
            
            PreparedStatement pstmtIns = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            if(existPersona_Asignatura) {            	
            	pstmtIns.setLong(1,r.getId_persona());
            	pstmtIns.setLong(2, r.getId_asignatura());
            	affectedRows = pstmtIns.executeUpdate();
            }
            
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rss = pstmtIns.getGeneratedKeys()) {
                    if (rss.next()) {
                        id = rss.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
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

    public long actualizar(Persona p) throws SQLException {

        String SQL = "UPDATE persona SET nombre = ? , apellido = ? WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getApellido());
            pstmt.setLong(3, p.getCedula());
 
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
    
    public long borrar(long cedula) throws SQLException {

        String SQL = "DELETE FROM persona WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, cedula);
 
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

	

	public List<String> listarAsignaturas(long cedula) {
		String SQL = "SELECT nombre FROM asignatura WHERE id IN (SELECT id_asignatura FROM persona_asignatura WHERE id_persona = ?) ";
		List<String> list = new ArrayList<String>();
		Connection conn = null;
		String nombre;
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, cedula);        	
        	ResultSet rs = pstmt.executeQuery();
        	while(rs.next()) {
        		nombre = new String(rs.getString(1));
        		list.add(nombre);
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
