package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Professor extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Integer siaep;
	
	public Professor() {
		
	}
	
	public Professor(Integer id, String nome, String cpf,String email, Integer siaep) {
		super(id, nome, cpf, email); 
		this.siaep = siaep;
	}

	public Integer getSiaep() {
		return siaep;
	}

	public void setSiaep(Integer siaep) {
		this.siaep = siaep;
	}
	
	public boolean validarEmail(String email) {
	    if (email == null) {
	        return false;
	    }
	    String regex = "^[a-zA-Z0-9]+@docente.[a-zA-Z]{2,}$";
	    return email.matches(regex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(siaep);
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
		Professor other = (Professor) obj;
		return Objects.equals(siaep, other.siaep);
	}

	@Override
	public String toString() {
        return "Professor [id=" + getId() + ", nome=" + getNome() + ", cpf=" + getCpf() + ", email=" + getEmail() + ", siaep=" + siaep + "]";
    }

	
	
	
}
