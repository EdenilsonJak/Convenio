package com.ejsistemas.lima.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ejsistemas.lima.modelo.Dependente;

public class DependenteRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	
	public Dependente porId(Long id) {
		return this.manager.find(Dependente.class, id);
		
	}
}
