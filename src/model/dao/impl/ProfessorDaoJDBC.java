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
import model.dao.ProfessorDao;
import model.entities.Professor;

public class ProfessorDaoJDBC implements ProfessorDao{
	
	private Connection conn;
	
	public ProfessorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Professor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO professor "
					+"(nome, cpf, email, siaep) "
					+ "VALUES "
					+"(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getSiaep());
			
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

	@Override
	public void update(Professor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE professor "
					+"SET nome=?, cpf=?, email=?,siaep=? "
					+ "WHERE id=?");
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getSiaep());
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

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM professor WHERE id = ?");
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

	@Override
	public Professor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM professor WHERE id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();	
			if(rs.next()) {
				Professor dep = instantiateProfessor(rs);
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

	private Professor instantiateProfessor(ResultSet rs) throws SQLException {
		Professor dep = new Professor();
		dep.setId(rs.getInt("id"));
		dep.setNome(rs.getString("nome"));
		dep.setCpf(rs.getString("cpf"));
		dep.setEmail(rs.getString("email"));
		dep.setSiaep(rs.getInt("siaep"));
		return dep;
	}

	@Override
	public List<Professor> findAll() {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    try {
	        st =conn.prepareStatement("SELECT * FROM professor");
	        rs = st.executeQuery();
	        
	        List<Professor> list = new ArrayList<>();
	        
	        while (rs.next()) {
	            Professor professor = instantiateProfessor(rs);
	            list.add(professor);
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
