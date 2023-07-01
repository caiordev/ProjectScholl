package model.dao;
import db.DB;
import model.dao.impl.AlunoDaoJDBC;
import model.dao.impl.CoordenadorDaoJDBC;
import model.dao.impl.DisciplinaDaoJDBC;
import model.dao.impl.ProfessorDaoJDBC;


//Fornecer instancias de diferentes implementações.
public class DaoFactory {
	//cada um desses métodos retorna uma instancia de uma implementação especifica da interface.
	//utilizei a classe DB para obter uma conexão com o banco de dados.
	public static ProfessorDao createProfessorDao() {
		return new ProfessorDaoJDBC(DB.getConnection()); 	
	}
	
	public static AlunoDao createAlunoDao() {
		return new AlunoDaoJDBC(DB.getConnection());
	}

	public static CoordenadorDao createCoordenadorDao() {
		return new CoordenadorDaoJDBC(DB.getConnection());
	}
	
	public static DisciplinaDao createDisciplinaDao() {
		return new DisciplinaDaoJDBC(DB.getConnection());
	}
	
	
	

}
