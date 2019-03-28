package com.ejsistemas.lima.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.ejsistemas.lima.modelo.Convenio;
import com.ejsistemas.lima.repository.ConvenioRepository;
import com.ejsistemas.lima.util.jpa.Transactional;

public class CadastrarConvenioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ConvenioRepository convenioRepository;
	
	@Transactional
	public Convenio salvar(Convenio convenio){
		return convenioRepository.guardar(convenio);
	}
	
}
