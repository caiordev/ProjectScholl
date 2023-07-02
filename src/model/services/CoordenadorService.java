package model.services;

import java.util.List;

import model.dao.CoordenadorDao;
import model.dao.DaoFactory;
import model.entities.Coordenador;

public class CoordenadorService {
	private CoordenadorDao dao = DaoFactory.createCoordenadorDao();
	public List<Coordenador> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Coordenador obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	
	public void remove(Coordenador obj) {
		dao.deleteById(obj.getId());
	}
}
