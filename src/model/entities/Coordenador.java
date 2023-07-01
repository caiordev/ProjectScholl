package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Coordenador extends Pessoa implements Serializable{
private static final long serialVersionUID = 1L;
	
	String departamento;
	
	public Coordenador() {
		
	}
	
	public Coordenador(Integer id, String nome, String cpf, String email, String departamento) {
		super(id, nome, cpf, email);
		this.departamento = departamento;
	}
	

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public boolean validarEmail(String email) {
	    if (email == null) {
	        return false;
	    }
	    String regex = "^[a-zA-Z0-9]+@coord.[a-zA-Z]{2,}$";
	    return email.matches(regex);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(departamento);
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
		Coordenador other = (Coordenador) obj;
		return Objects.equals(departamento, other.departamento);
	}

	@Override
	public String toString() {
        return "Coordenador [id=" + getId() + ", nome=" + getNome() + ", cpf=" + getCpf() + ", email=" + getEmail() + ", departamento=" + departamento + "]";
    }



	
	

}
