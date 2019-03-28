package com.ejsistemas.lima.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.modelo.Convenio;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.repository.ConvenioRepository;
import com.ejsistemas.lima.repository.filter.ClienteFilter;
import com.ejsistemas.lima.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	
	@Inject
	ClienteRepository repository;
	
	@Inject
	ConvenioRepository convenioRepository;
	
	private List<Convenio> convenios;
	
	
	private ClienteFilter clienteFilter;
	private List<Cliente> clientesFiltrados;
	
	public PesquisaClienteBean(){
		limpar();
		//cliente = new Cliente();
	}
	
	public void limpar(){
		clienteFilter = new ClienteFilter();
		convenios = new ArrayList<>();
		this.cliente = new Cliente();
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Convenio> getConvenios() {
		return convenios;
	}

	public ClienteFilter getClienteFilter() {
		return clienteFilter;
	}
	
	public void setClienteFilter(ClienteFilter clienteFilter) {
		this.clienteFilter = clienteFilter;
	}


	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}
	
	public void pesquisar(){
		clientesFiltrados = repository.filtrados(clienteFilter);
	}
	
	public List<Cliente> completarCliente(String nome){
		return this.repository.porNome(nome);
	}
	
	public List<Convenio> pesquisarConvenio(){
		
		if(this.cliente != null){
			convenios = convenioRepository.filtrados(this.cliente);
		}else{
			FacesUtil.addAlerMessage("Por favor! digite um cliente para pesquisar convênio!");
		}		
		
		return convenios;
		
	}

}
