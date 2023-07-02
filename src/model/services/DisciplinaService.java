package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DisciplinaDao;
import model.entities.Disciplina;

public class DisciplinaService {
	private DisciplinaDao dao = DaoFactory.createDisciplinaDao();
	public List<Disciplina> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Disciplina obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	
	public void remove(Disciplina obj) {
		dao.deleteById(obj.getId());
	}
}
