package com.ejsistemas.lima.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ejsistemas.lima.modelo.Grupo_usuario;

public class Grupo_usuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager managar;
	
	public Grupo_usuario guardar(Grupo_usuario grupo_usuario){
		return managar.merge(grupo_usuario);
	}

	public Grupo_usuario porId(Long id) {
		return this.managar.find(Grupo_usuario.class, id);
	}

}
