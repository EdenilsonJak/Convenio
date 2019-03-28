package com.ejsistemas.lima.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.ejsistemas.lima.util.jpa.Transactional;
import com.ejsistemas.lima.modelo.Cliente;
import com.ejsistemas.lima.repository.ClienteRepository;
import com.ejsistemas.lima.util.jsf.FacesUtil;

public class CadastrarClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ClienteRepository pacienteRepository;

	@Transactional
	public Cliente salvar(Cliente paciente) {

		if (paciente.getId_cliente() != null) {

			FacesUtil.addInfoMessage("Cliente Atualizado com sucesso!");
		} else {

			FacesUtil.addInfoMessage("Cliente Cadastrado com sucesso!!");
		}

		return pacienteRepository.guardar(paciente);

	}

}
