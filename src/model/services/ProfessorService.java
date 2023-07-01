package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProfessorDao;
import model.entities.Professor;

public class ProfessorService {
	private ProfessorDao dao = DaoFactory.createProfessorDao();
	public List<Professor> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Professor obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	
	public void remove(Professor obj) {
		dao.deleteById(obj.getId());
	}
}
