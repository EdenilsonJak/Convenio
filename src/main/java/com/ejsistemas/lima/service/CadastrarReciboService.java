package com.ejsistemas.lima.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.ejsistemas.lima.modelo.Contas;
import com.ejsistemas.lima.repository.ReciboRepository;
import com.ejsistemas.lima.util.jpa.Transactional;

public class CadastrarReciboService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	ReciboRepository reciboRepository;
	
	@Transactional
	public Contas salvar(Contas recibo){
		return reciboRepository.guardar(recibo);
	}
	
	@Transactional
	public void excluir(Contas recibo){
		reciboRepository.remover(recibo);
	}

}
