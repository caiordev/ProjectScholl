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
import model.dao.DisciplinaDao;
import model.entities.Disciplina;

public class DisciplinaDaoJDBC implements DisciplinaDao{
	
	private Connection conn;
	
	public DisciplinaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Disciplina obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO disciplina "
					+"(nome, professor, curso, vagas) "
					+ "VALUES "
					+"(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getProfessor());
			st.setString(3, obj.getCurso());
			st.setInt(4, obj.getVagas());
			
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
	public void update(Disciplina obj) {
		if (obj == null) {
	        throw new IllegalArgumentException("O objeto não pode ser nulo.");
	    }
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE disciplina "
					+"SET nome=?, professor=?, curso=?, vagas=? "
					+ "WHERE id=?");
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getProfessor());
			st.setString(3, obj.getCurso());
			st.setInt(4, obj.getVagas());
			 if (obj.getId() != null) {
		            st.setInt(5, obj.getId().intValue());
		        } else {
		            st.setNull(5, java.sql.Types.INTEGER);
		        }
			
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
			st = conn.prepareStatement("DELETE FROM disciplina WHERE id = ?");
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
	public Disciplina findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM disciplina WHERE id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();	
			if(rs.next()) {
				Disciplina dep = instantiateDisciplina(rs);
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

	private Disciplina instantiateDisciplina(ResultSet rs) throws SQLException {
		Disciplina dep = new Disciplina();
		dep.setId(rs.getInt("id"));
		dep.setNome(rs.getString("nome"));
		dep.setProfessor(rs.getString("professor"));
		dep.setCurso(rs.getString("curso"));
		dep.setVagas(rs.getInt("vagas"));
		return dep;
	}

	@Override
	public List<Disciplina> findAll() {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    try {
	        st =conn.prepareStatement("SELECT * FROM disciplina");
	        rs = st.executeQuery();
	        
	        List<Disciplina> list = new ArrayList<>();
	        
	        while (rs.next()) {
	            Disciplina disciplina = instantiateDisciplina(rs);
	            list.add(disciplina);
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
