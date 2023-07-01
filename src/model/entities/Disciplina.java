package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String professor;
	private String curso;
	private Integer vagas;
	
	public Disciplina() {
		
	}
	public Disciplina(Integer id, String nome, String professor, String curso, Integer vagas) {
		super();
		this.id = id;
		this.nome = nome;
		this.professor = professor;
		this.curso = curso;
		this.vagas = vagas;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public Integer getVagas() {
		return vagas;
	}
	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}
	@Override
	public int hashCode() {
		return Objects.hash(curso, id, nome, professor, vagas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		return Objects.equals(curso, other.curso) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(professor, other.professor) && Objects.equals(vagas, other.vagas);
	}
	@Override
	public String toString() {
		return "Disciplina [id=" + id + ", nome=" + nome + ", professor=" + professor + ", curso=" + curso + ", vagas="
				+ vagas + "]";
	}
	
	
	
	
	
	

}
