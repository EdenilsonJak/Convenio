package com.ejsistemas.lima.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_contrato")
public class Convenio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id_contrato;
	private Date data;
	private String status = "Inicial";
	private Cliente cliente;
	private String codinterno;
	private List<Contas> contas = new ArrayList<Contas>();
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	public Long getId_contrato() {
		return id_contrato;
	}
	public void setId_contrato(Long id_contrato) {
		this.id_contrato = id_contrato;
	}
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL , orphanRemoval = true)
	public List<Contas> getContas() {
		return contas;
	}
	public void setContas(List<Contas> contas) {
		this.contas = contas;
	}
	
	@Column(length = 50) 
	public String getCodinterno() {
		return codinterno;
	}
	public void setCodinterno(String codinterno) {
		this.codinterno = codinterno;
	}
	@Transient
	public boolean isNovo(){
		return getId_contrato() == null;
	}
	
	@Transient
	public boolean isExistente(){
		return !isNovo();
	}
	
	@Transient
	public boolean isEmitido(){
		return getStatus().equals("Emitido");
	}
	
	@Transient
	public boolean isInicial(){
		return getStatus().equals("Inicial");
	}
	
	@Transient
	public boolean isNotInicial(){
		return !isInicial();
	}
	
	@Transient
	public boolean isEmissivel(){
		return getId_contrato() != null && isInicial() || isCancelado();
	}
	
	@Transient
	public boolean isCancelavel(){
		return isExistente() && !isCancelado();
	}
	
	@Transient
	public boolean isCancelado(){
		return getStatus().equals("Cancelado");
	}
	
	@Transient
	public boolean isProdutoAssociado() {
		return this.getCliente() != null && this.getCliente().getId_cliente() != null;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_contrato == null) ? 0 : id_contrato.hashCode());
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
		Convenio other = (Convenio) obj;
		if (id_contrato == null) {
			if (other.id_contrato != null)
				return false;
		} else if (!id_contrato.equals(other.id_contrato))
			return false;
		return true;
	}
	
	

}
