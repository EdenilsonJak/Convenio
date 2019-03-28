package com.ejsistemas.lima.repository.filter;

import java.util.Date;

import com.ejsistemas.lima.modelo.Cliente;

public class ReciboFilter{

	private Long codigo;
	private Date datade;
	private Date dateate;
	private Date vencimento;
	private Date vencimentoate;
	private String status = null;
	private Cliente cliente;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Date getDatade() {
		return datade;
	}
	public void setDatade(Date datade) {
		this.datade = datade;
	}
	public Date getDateate() {
		return dateate;
	}
	public void setDateate(Date dateate) {
		this.dateate = dateate;
	}
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getVencimentoate() {
		return vencimentoate;
	}
	public void setVencimentoate(Date vencimentoate) {
		this.vencimentoate = vencimentoate;
	}
	
	
	
	
	
	
	
}
