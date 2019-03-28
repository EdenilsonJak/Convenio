package com.ejsistemas.lima.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ejsistemas.lima.modelo.Grupo;

public class GrupoRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager managar;
	
	public List<Grupo> grupos(){
		return this.managar.createQuery("from Grupo", Grupo.class)
				.getResultList();
	}

	public List<Grupo> porNome(String nome) {
		return this.managar.createQuery("from Grupo where upper(nome) like :nome", Grupo.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}

	public Grupo porId(Long id) {
		return this.managar.find(Grupo.class, id);
	}

}
