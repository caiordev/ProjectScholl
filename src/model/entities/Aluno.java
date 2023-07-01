package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Aluno extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	Integer matricula;
	
	public Aluno() {
		
	}
	
	public Aluno(Integer id, String nome, String cpf, String email,  Integer matricula) {
		super(id, nome, cpf, email);
		this.matricula = matricula;
	}
	

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	public boolean validarEmail(String email) {
	    if (email == null) {
	        return false;
	    }
	    String regex = "^[a-zA-Z0-9]+@discente.[a-zA-Z]{2,}$";
	    return email.matches(regex);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(matricula);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(matricula, other.matricula);
	}

	@Override
	public String toString() {
        return "Aluno [id=" + getId() + ", nome=" + getNome() + ", cpf=" + getCpf() + ", email=" + getEmail() + ", matricula=" + matricula + "]";
    }


}
