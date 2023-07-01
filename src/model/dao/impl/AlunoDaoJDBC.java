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
import model.dao.AlunoDao;
import model.entities.Aluno;


public class AlunoDaoJDBC implements AlunoDao{
	private Connection conn;
	
	public AlunoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void insert(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO aluno "
					+"(nome, cpf, email, numero, matricula) "
					+ "VALUES "
					+"(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getMatricula());
			
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

	public void update(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE aluno "
					+"SET nome=?, cpf=?, email=?, matricula=? "
					+ "WHERE id=?");
		
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getMatricula());
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
			st = conn.prepareStatement("DELETE FROM aluno WHERE id = ?");
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

	public Aluno findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM aluno WHERE id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();	
			if(rs.next()) {
				Aluno dep = instantiateAluno(rs);
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

	private Aluno instantiateAluno(ResultSet rs) throws SQLException {
		Aluno dep = new Aluno();
		dep.setId(rs.getInt("id"));
		dep.setNome(rs.getString("nome"));
		dep.setCpf(rs.getString("cpf"));
		dep.setEmail(rs.getString("email"));
		dep.setMatricula(rs.getInt("matricula"));
		return dep;
	}

	public List<Aluno> findAll() {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    try {
	        st =conn.prepareStatement("SELECT * FROM aluno");
	        rs = st.executeQuery();
	        
	        List<Aluno> list = new ArrayList<>();
	        
	        while (rs.next()) {
	            Aluno aluno = instantiateAluno(rs);
	            list.add(aluno);
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
