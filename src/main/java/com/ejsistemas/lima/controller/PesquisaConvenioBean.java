package com.ejsistemas.lima.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.util.jsf.FacesUtil;

public class PesquisaConvenioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;

	@Inject
	ClienteRepository clienteRepository;
	
	public PesquisaConvenioBean(){
		cliente = new Cliente();
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	public List<Cliente> completarCliente(String nome){
		List<Cliente> clientes = clienteRepository.porNome(nome);
		if(clientes.size() == 0){
			FacesUtil.addAlerMessage("Cliente não encontrado!");
		}
		return clientes;
	}
}
