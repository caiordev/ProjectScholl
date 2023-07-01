package model.entities;

import java.io.Serializable;

import java.util.Objects;


public abstract class Pessoa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;
    private String cpf;
    private String email;

    public Pessoa() {
    }

    public Pessoa(Integer id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }
    
    
    public void setEmail(String email) {
        if (validarEmail(email)) {
            this.email = email;
        } else {
            System.out.println("Email inválido.");
        }
    }
    
    public abstract boolean validarEmail(String email);

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Pessoa [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + "]";
    }
}
