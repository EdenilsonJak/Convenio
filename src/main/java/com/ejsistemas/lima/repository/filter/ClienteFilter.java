package com.ejsistemas.lima.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.ejsistemas.lima.modelo.Cliente;

public class ClienteFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String profissao;
	private Date dataCadastroDe;
	private Date dataCadastroAte;
	private Date dataNascimento;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public Date getDataCadastroDe() {
		return dataCadastroDe;
	}
	public void setDataCadastroDe(Date dataCadastroDe) {
		this.dataCadastroDe = dataCadastroDe;
	}
	public Date getDataCadastroAte() {
		return dataCadastroAte;
	}
	public void setDataCadastroAte(Date dataCadastroAte) {
		this.dataCadastroAte = dataCadastroAte;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	

}
