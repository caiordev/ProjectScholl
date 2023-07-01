package model.dao;

import java.util.List;

import model.entities.Coordenador;

public interface CoordenadorDao {
	void insert(Coordenador obj);
	void update(Coordenador obj);
	void deleteById(Integer id);
	Coordenador findById(Integer id);
	List<Coordenador> findAll();

}


