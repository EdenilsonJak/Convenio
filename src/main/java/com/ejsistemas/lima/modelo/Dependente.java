package com.ejsistemas.lima.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="tb_dependente")
public class Dependente  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id_dependente;
	private String dependente;
	private Cliente paciente;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	public Long getId_dependente() {
		return id_dependente;
	}
	public void setId_dependente(Long id_dependente) {
		this.id_dependente = id_dependente;
	}

	@Column(name="dependente")
	public String getNome() {
		return dependente;
	}
	public void setNome(String nome) {
		this.dependente = nome;
	}
	
	@ManyToOne()
	@JoinColumn(name = "cliente_id")
	public Cliente getPaciente() {
		return paciente;
	}
	public void setPaciente(Cliente paciente) {
		this.paciente = paciente;
	}
	
	@PreUpdate
	@PrePersist
	public void toUpper(){
		if(this.dependente != null){
			this.setNome(getNome().toUpperCase());
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_dependente == null) ? 0 : id_dependente.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dependente other = (Dependente) obj;
		if (id_dependente == null) {
			if (other.id_dependente != null)
				return false;
		} else if (!id_dependente.equals(other.id_dependente))
			return false;
		return true;
	}

	
}
