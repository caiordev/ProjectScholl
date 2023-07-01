package model.dao;

import java.util.List;

import model.entities.Professor;

public interface ProfessorDao {
	void insert(Professor obj);
	void update(Professor obj);
	void deleteById(Integer id);
	Professor findById(Integer id);
	List<Professor> findAll();
	public static boolean validarEmail(String email) {
	    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	    return email.matches(regex);
	}
}
