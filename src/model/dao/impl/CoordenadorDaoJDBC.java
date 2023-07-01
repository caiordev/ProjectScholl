package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CoordenadorDao;
import model.entities.Coordenador;

public class CoordenadorDaoJDBC implements CoordenadorDao{
	private Connection conn;
	
	public CoordenadorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void insert(Coordenador obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO coordenador "
					+"(nome, cpf, email, numero, departamento) "
					+ "VALUES "
					+"(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getDepartamento());
			
			int rowAffected = st.executeUpdate();
			
			if(rowAffected>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("unexpected error! no rows affected");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
	}

	public void update(Coordenador obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE aluno "
					+"SET nome=?, cpf=?, email=?, departamento=?) "
					+ "WHERE id=?");
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getDepartamento());
			st.setInt(5, obj.getId());
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM coordenador WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	public Coordenador findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM coordenador WHERE id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();	
			if(rs.next()) {
				Coordenador dep = instantiateCoordenador(rs);
				return dep;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	private Coordenador instantiateCoordenador(ResultSet rs) throws SQLException {
		Coordenador dep = new Coordenador();
		dep.setId(rs.getInt("id"));
		dep.setNome(rs.getString("nome"));
		dep.setCpf(rs.getString("cpf"));
		dep.setEmail(rs.getString("email"));
		dep.setDepartamento(rs.getString("departamento"));
		return dep;
	}

	public List<Coordenador> findAll() {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    try {
	        st =conn.prepareStatement("SELECT * FROM coordenador");
	        rs = st.executeQuery();
	        
	        List<Coordenador> list = new ArrayList<>();
	        
	        while (rs.next()) {
	            Coordenador coordenador = instantiateCoordenador(rs);
	            list.add(coordenador);
	        }
	        
	        return list;
	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeResultSet(rs);
	        DB.closeStatement(st);
	    }
	}

}
